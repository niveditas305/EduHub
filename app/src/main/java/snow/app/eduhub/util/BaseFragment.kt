package snow.app.eduhub.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import snow.app.eduhub.ui.LoginActivity
import snow.app.eduhub.viewmodels.BaseViewModel

open class BaseFragment() : Fragment() , DialogInterface.OnDismissListener{
    open lateinit var dialog: ProgressDialog
    lateinit var viewModell: BaseViewModel
    var mContext:Activity?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        viewModell = BaseViewModel()
        dialog = ProgressDialog(context)
        dialog?.setMessage("Please wait...")
        dialog?.setCancelable(false)

    }

    fun getUserToken(): String {
        Log.e("AppSession", "--" + AppSession(requireContext()).getAppData()?.data)
        return "Bearer " + AppSession(requireContext()).getAppData()?.data?.token.toString()


    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    open fun getSession(): AppSession? {
        return AppSession(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = ProgressDialog(context)
        dialog?.setMessage("Please wait...")
        dialog?.setCancelable(false)
        viewModell = BaseViewModel()

    }

    open fun goToNextWithClearBackStack(cls: Class<*>?) {
        val i = Intent(context, cls)
        // set the new task and clear flags
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context?.startActivity(i)
        // overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        activity?.finish()
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
    fun showTokenError(context: Activity){
        mContext = context
        var alert = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
        alert.setTitleText("Alert")
        alert.setContentText("Session Expired,Please login again!")
        alert.setOnDismissListener(this)
        alert.show()
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


    fun isNetworkConnected(): Boolean {
        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun showInternetToast() {
        Toast.makeText(requireContext(), "No internet connection !", Toast.LENGTH_SHORT).show()
    }

//    fun showEntryTypeAlert(context: Context){
//        AppUtils.showCustomDialog(context)
//    }

    fun getDeviceToken(): String {
        var device_token: String = ""
        device_token =
            Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)


        /* device_token = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("device toekn--" + device_token);*/
        Log.e("devicetoken", "--" + device_token)

        /*  FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this
          ) { instanceIdResult -> device_token  = instanceIdResult.token  }
          return device_token*/

        return device_token
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

    override fun onDismiss(dialog: DialogInterface?) {

        getSession()?.logout()
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }

    fun refreshFragment(){
        getFragmentManager()?.beginTransaction()?.detach(this)?.attach(this)?.commit();

    }

}