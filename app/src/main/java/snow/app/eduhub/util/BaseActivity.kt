package snow.app.eduhub.util


import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import snow.app.eduhub.ui.LoginActivity
import snow.app.eduhub.viewmodels.BaseViewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


open class BaseActivity : AppCompatActivity(), DialogInterface.OnDismissListener  {
    val RC_SIGN_IN = 1
    lateinit var viewModell: BaseViewModel
    var callbackManager: CallbackManager? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    lateinit var dialog: ProgressDialog
    lateinit var sharedPreferences: SharedPreferences
    var device_token: String = ""
    var lastname: String = ""
    fun getUserToken(): String {
        Log.e("AppSession", "--" + AppSession(this).getAppData()?.data)
        return "Bearer " + AppSession(this).getAppData()?.data?.token.toString()


    }

    fun isNetworkConnected(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    open fun getSession(): AppSession? {
        return AppSession(this)
    }
    fun getDeviceToken(): String {

        //  device_token = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)


        /* device_token = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("device toekn--" + device_token);*/

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
            this
        ) { instanceIdResult ->

            device_token = instanceIdResult.token
            Log.v("token", device_token)
            var editor: SharedPreferences.Editor = sharedPreferences.edit();

            editor.putString("token", device_token);
            editor.commit();
        }
        return device_token
        Log.e("devicetoken", "fire--" + device_token)

        //    return device_token
    }


    /*fun getDeviceToken(): String {

        //  device_token = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

  device_token = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("device toekn--" + device_token);

        *//*   FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this
           ) { instanceIdResult -> device_token  = instanceIdResult.token

    var editor:SharedPreferences.Editor  = sharedPreferences.edit();

                editor.putString("token", device_token);
               editor.commit();
           }
           return device_token
           Log.e("devicetoken","fire--"+device_token)
   *//*
        return device_token
    }*/
    fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = ProgressDialog(this)
        dialog?.setMessage("Please wait...")
        dialog?.setCancelable(false)
        sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE);
        callbackManager = CallbackManager.Factory.create()
        viewModell = BaseViewModel()
        getDeviceToken()
//google login init
        googleLoginInit()

        //  getFacebookLoginHash()

    }
    override fun onDismiss(dialog: DialogInterface?) {
        getSession()?.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showInternetToast() {
        Toast.makeText(this, "No internet connection !", Toast.LENGTH_SHORT).show()
    }

    open fun goToNextWithClearBackStack(cls: Class<*>?) {
        val i = Intent(this, cls)
        // set the new task and clear flags
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        // overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        finish()
    }

    fun showError(msg: String, context: Context) {

        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText(msg)
            .show();
    }

    fun showWarning(msg: String, context: Context) {
        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Warning")
            .setContentText(msg)
            .show();


    }

    fun showSuccess(msg: String, context: Context) {

        SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Success")
            .setContentText(msg)
            .show();
    }

    fun showAlert(msg: String, context: Context) {
        SweetAlertDialog(context)
            .setTitleText(msg)
            .show();

    }

    fun refreshActivityWithoutAnimation() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    override fun onPause() {
        super.onPause()
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    fun showTokenError() {

        var alert = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        alert.setTitleText("Alert")
        alert.setContentText("Session Expired,Please login again!")
        alert.setOnDismissListener(this)
        alert.show()
    }

    fun googleLoginInit() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()



        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    fun googleLogin() {
        signOut()
        signIn()
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun signOut() {
        mGoogleSignInClient!!.signOut()
            .addOnCompleteListener(this) { /*Toast.makeText(Login.this, "User logged out", Toast.LENGTH_SHORT).show();*/ }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) { // The Task returned from this call is always completed, no need to attach
// a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
//            // Signed in successfully, show authenticated UI.
            Log.wtf("response g", "signInResult" + account?.photoUrl)


            Log.wtf("name", "signInResult" + account?.givenName)






            viewModell.googleLogin(
                account?.email.toString(),
                account?.givenName.toString(), "",
                account?.photoUrl.toString(),
                "",
                account?.id.toString()
                , getDeviceToken(),""
            )


        } catch (e: ApiException) {
            Log.wtf("cxv", "signInResult:failed code=" + e.statusCode)
        }
    }


    fun getFacebookLoginHash() {
        val info: PackageInfo
        try {
            info = packageManager.getPackageInfo("snow.app.eduhub", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("no such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }
    }


    fun facebookInit() {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {


                    // App code


                    val request = GraphRequest.newMeRequest(loginResult!!.accessToken)
                    { `object`, response ->
                        try {
                            Log.d("fb res--", `object`.toString())
                            if (`object`.has("id")) {
                                val givenName: String = `object`.getString("name")
                                val namearray = givenName.split(" ").toTypedArray()
                                if (namearray.size == 2) {


                                    if (namearray[1] == null) {

                                        lastname = ""
                                    } else {
                                        lastname = namearray[1]
                                    }
                                } else {
                                    lastname = ""
                                }
                                val fid = `object`.getString("id")
                                val email: String = `object`.optString("email")


                                Log.e("res--", "--" + email)
                                if (email == null || email.isEmpty()) {
                                    showToast("Email required! Please login with email in facebook app")
                                } else {
                                    Log.d(
                                        "image--",
                                        "https://graph.facebook.com/" + fid + "/picture?type=large"
                                    )
// Log.e("FBLOGIN_FAILD", object.getString(""))


                                    viewModell.facebookLogin(
                                        email,
                                        namearray[0].trim(), lastname,
                                        "https://graph.facebook.com/" + fid +
                                                "/picture?type=large",
                                        "",
                                        fid,
                                        getDeviceToken(),""

                                    )
                                }


                            } else {

                                Log.e("FBLOGIN_FAILD", `object`.toString())
                            }


                            LoginManager.getInstance().logOut()


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }


                    val parameters = Bundle()
                    parameters.putString("fields", "name,email,id,picture.type(large)")

                    request.parameters = parameters
                    request.executeAsync()

                }

                override fun onCancel() { // App code
                }

                override fun onError(exception: FacebookException) { // App code
                }


            })
    }
}