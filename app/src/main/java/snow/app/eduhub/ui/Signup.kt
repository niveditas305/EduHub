package snow.app.eduhub.ui

import Constants.Companion.LOGINN
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.facebook.login.LoginManager
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import snow.app.eduhub.MainActivity
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivitySignupBinding
import snow.app.eduhub.network.responses.grades.Data
import snow.app.eduhub.util.AppSession
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.SignupVm
import java.util.*
import kotlin.collections.ArrayList

class  Signup : BaseActivity() {
    var gradelist: ArrayList<String> = ArrayList()
    lateinit var grades: List<Data>
    var viewModel: SignupVm? = null
    lateinit var binding: ActivitySignupBinding

    lateinit var powerMenu: PowerMenu
    var deviceTokenn: String = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_signup)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.lifecycleOwner = this


        var sharedPreferences: SharedPreferences =
            this.getSharedPreferences("token", Context.MODE_PRIVATE);

        deviceTokenn = sharedPreferences.getString("token", "").toString()
        viewModel = SignupVm(deviceTokenn)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        val locale: String = getResources().getConfiguration().locale.getCountry()
        Log.e("locale", "--" + locale)
        binding.ccp.setDefaultCountryUsingNameCode(locale)
        binding.tvSignup.setOnClickListener {

            if (isNetworkConnected()) {
                viewModel!!.onSignupClick(binding.ccp.selectedCountryCode)
            } else {
                showInternetToast()
            }


        }

        binding.grade.setOnClickListener {
            show(gradelist, binding.grade)
        }

        binding.tvlogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


//        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this
//        ) { instanceIdResult -> device_token  = instanceIdResult.token
//            Log.e("devicetoken","loginn--"+device_token)
//
//        }


        viewModel?.isLoading?.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel?.isError?.observe(this, Observer {
            if (it.isError) {
                showError(it.message, this);
            }

        })



        viewModel?.respData?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {

                    Log.e("status true", "login--")
                    dialog.dismiss()
                    val session: AppSession = AppSession(this)
                    session.saveSession(it);
                    startActivity(Intent(this, VerificationScreen::class.java))
                    finish()
                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    //  showError(it.message, this)
                }
            }

        })

        viewModel?.respDataGrade?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    grades = it.data

                    //  list.add("Grade")
                    for (x in 0 until grades.size) {
                        gradelist.add(grades.get(x).class_name.toString())
                    }

                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    //   showError(it.message, this)
                }
            }

        })



        binding.ivGSignup.setOnClickListener {

            if (isNetworkConnected()) {
                googleLogin()
            } else {
                showInternetToast()
            }
        }


        binding.ivInvisible.setOnClickListener(View.OnClickListener {
            binding.edPass.setTransformationMethod(PasswordTransformationMethod())
            binding.ivVisible.setVisibility(View.VISIBLE)
            binding.ivInvisible.setVisibility(View.GONE)

            binding.edPass.setSelection(binding.edPass.getText().toString().length);
        })

        binding.ivVisible.setOnClickListener(View.OnClickListener {

            binding.edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance())

            binding.ivInvisible.setVisibility(View.VISIBLE)
            binding.ivVisible.setVisibility(View.GONE)
            binding.edPass.setSelection(binding.edPass.getText().toString().length);
        })
        binding.ivReinvisible.setOnClickListener(View.OnClickListener {
            binding.edRepass.setTransformationMethod(PasswordTransformationMethod())
            binding.ivRevisible.setVisibility(View.VISIBLE)
            binding.ivReinvisible.setVisibility(View.GONE)

            binding.edRepass.setSelection(binding.edRepass.getText().toString().length);
        })

        binding.ivRevisible.setOnClickListener(View.OnClickListener {

            binding.edRepass.setTransformationMethod(HideReturnsTransformationMethod.getInstance())

            binding.ivReinvisible.setVisibility(View.VISIBLE)
            binding.ivRevisible.setVisibility(View.GONE)
            binding.edRepass.setSelection(binding.edRepass.getText().toString().length);
        })


        //face book init
        if (isNetworkConnected()) {
            facebookInit()
        } else {
            showInternetToast()
        }

        //get grades
        if (isNetworkConnected()) {
            viewModel!!.getGrades()
        } else {
            showInternetToast()
        }


        //response handle google and facebook
        viewModell.respData_google.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {


                if (it.status) {
                    Log.e("status true", "login--")
                    dialog.dismiss()
                    val session: AppSession = AppSession(this)
                    session.saveSession(it);
                    session.saveEntryType(LOGINN)
                    if (it.data.studentStatus == 0) {
                        val intent = Intent(this, VerificationScreen::class.java)
                        startActivity(intent)
                        // startActivity(Intent(this, VerificationScreen::class.java))
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    }
                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    showError(it.message, this)
                }
            }

        })
        viewModell.respDataa_fb.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {


                if (it.status) {
                    Log.e("status true", "login--")
                    dialog.dismiss()
                    val session: AppSession = AppSession(this)
                    session.saveSession(it);
                    session.saveEntryType(LOGINN)
                    if (it.data.studentStatus == 0) {
                        startActivity(Intent(this, VerificationScreen::class.java))
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    }
                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    showError(it.message, this)
                }
            }

        })


//facebook login click
        iv_f_signup.setOnClickListener {
            if (isNetworkConnected()) {
                LoginManager.getInstance().logOut()
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"))
            } else {
                showInternetToast()
            }

        }

    }


    fun show(list: List<String>, view: View?) {
        val l1: MutableList<PowerMenuItem> = ArrayList()
        for (i in list.indices) {
            l1.add(PowerMenuItem(list[i], false))
        }
        powerMenu = PowerMenu.Builder(this)
            .addItemList(l1)
            .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
            .setMenuRadius(10f)
            .setMenuShadow(10f)
            .setHeaderView(R.layout.powermenu_header)
            .setTextColor(ContextCompat.getColor(this, R.color.black))
            .setTextGravity(Gravity.CENTER)
            .setTextTypeface(Typeface.create(getResources().getFont(R.font.semi), Typeface.NORMAL))
            .setSelectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setMenuColor(Color.WHITE)
            .setSelectedMenuColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setOnMenuItemClickListener(onMenuItemClickListener)
            .build()
        powerMenu.showAtCenter(view)
    }

    private val onMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        val textSelected = item.title
        viewModel?.grade?.set(textSelected)

        for (i in 0 until grades.size) {


            if (textSelected.equals(grades.get(i).class_name)) {
                viewModel?.gradeid?.set(grades.get(i).id.toString())
            }


        }

        powerMenu.dismiss()
    }


//
//    fun googleLoginInit(){
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//    }


//    fun googleLogin(){
//        signOut()
//        signIn()
//    }
//    private fun signIn() {
//        val signInIntent = mGoogleSignInClient!!.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }


//    private fun signOut() {
//        mGoogleSignInClient!!.signOut()
//            .addOnCompleteListener(this) { /*Toast.makeText(Login.this, "User logged out", Toast.LENGTH_SHORT).show();*/ }
//    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) { // The Task returned from this call is always completed, no need to attach
//// a listener.
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        } else {
//            // callbackManager?.onActivityResult(requestCode, resultCode, data)
//        }
//    }

//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
////            // Signed in successfully, show authenticated UI.
//            Log.wtf("response g", "signInResult" + account?.photoUrl)
//
//
//            Log.wtf("name", "signInResult" + account?.givenName)
//
//
//            viewModel?.googleLogin(account?.email.toString(), account?.givenName.toString(), account?.photoUrl.toString(), account?.id.toString()
//            )
//
//
//        } catch (e: ApiException) {
//            Log.wtf("cxv", "signInResult:failed code=" + e.statusCode)
//        }
//    }
}
