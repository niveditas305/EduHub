package snow.app.eduhub.ui

import Constants.Companion.LOGINN
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_login.*
import snow.app.eduhub.MainActivity
import snow.app.eduhub.R
import snow.app.eduhub.util.AppSession
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.LoginViewModel
import java.util.*

class LoginActivity : BaseActivity() {
    lateinit var viewModel: LoginViewModel
    var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var sharedPreferences: SharedPreferences =
            this.getSharedPreferences("token", Context.MODE_PRIVATE);

        token = sharedPreferences.getString("token", "").toString()


        viewModel = LoginViewModel(token)



        viewModel.isLoading.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel.isError.observe(this, Observer {
            if (it.isError) {
                showError(it.message, this);
            }

        })

        tv_signup.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
        tv_forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }




        iv_invisible.setOnClickListener(View.OnClickListener {
            password.setTransformationMethod(PasswordTransformationMethod())
            iv_visible.setVisibility(View.VISIBLE)
            iv_invisible.setVisibility(View.GONE)

            password.setSelection(password.getText().toString().length);
        })

        iv_visible.setOnClickListener(View.OnClickListener {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance())

            iv_invisible.setVisibility(View.VISIBLE)
            iv_visible.setVisibility(View.GONE)
            password.setSelection(password.getText().toString().length);
        })

        tv_login.setOnClickListener {


            if (isNetworkConnected()) {
                if (email.text.toString().isEmpty()) {
                    showToast("Please enter valid email!")
                } else if (password.text.toString().isEmpty()) {
                    showToast("Please enter valid password!")
                } else {
                    viewModel.loginUser(email.text.toString(), password.text.toString())

                }
            } else {
                showInternetToast()
            }
            //startActivity(Intent(this,MainActivity::class.java))
        }


//facebook init
        if (isNetworkConnected()) {
            facebookInit()
        } else {
            showInternetToast()
        }


        //google login click
        iv_google.setOnClickListener {

            if (isNetworkConnected()) {
                googleLogin()
            } else {
                showInternetToast()
            }
        }

//facebook login click
        iv_fb_login.setOnClickListener {
            if (isNetworkConnected()) {
                LoginManager.getInstance().logOut()
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"))
            } else {
                showInternetToast()
            }

        }

        /*  viewModel.respData.observe(this, Observer {
              Log.e("respData ", "login--")
              if (it != null) {
                  if (it.status) {
                      dialog.dismiss()
                      val session: AppSession = AppSession(this)
                      session.saveSession(it);
                      showToast(it.message)
                      startActivity(Intent(this, MainActivity::class.java))
                      finish()
                  } else {
                      Log.e("statusfalse", "login--")

                      showError(it.message, this)
                  }
              }

          })
  */



        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {


                if (it.status) {
                    Log.e("status true", "login--")
                    dialog.dismiss()
                    val session: AppSession = AppSession(this)
                    session.saveSession(it);
                    session.saveEntryType(LOGINN)
                    if (it.data.studentStatus == 0) {
                     //   startActivity(Intent(this, VerificationScreen::class.java))

                        val intent=Intent(this,VerificationScreen::class.java)
                        intent.putExtra("from","login")
                        startActivity(intent)
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
                        startActivity(Intent(this, VerificationScreen::class.java))
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    }
                } else {
                    Log.e("statusfalse", "login--")

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

                    showError(it.message, this)
                }
            }

        })


    }
}