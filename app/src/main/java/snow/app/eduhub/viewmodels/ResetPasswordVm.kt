package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.forgotpassres.ForgotPassRes
import snow.app.eduhub.util.AlertModel


class ResetPasswordVm(var sessiontoken:String):BaseViewModel( ){
    val respData = MutableLiveData<BaseRes>(null)
    var pin = ObservableField("")
      var newPass = ObservableField("")
      var email = ObservableField("")



    // interface call back for api call
    val onDataReadyCallbackk = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as BaseRes?
                if (d?.status!!){
                    Log.e("status","status");
                    respData.postValue(d)


                }else{
                    isError.postValue(AlertModel(d.message.toString(),true));
                    Log.e("status","e");
                }






            }else{
                isError.postValue(AlertModel("Something went wrong.",true));
                Log.e("status","ee");
            }

        }
    }




    fun onPinTextChanged(text: CharSequence?) {
              pin.set(text.toString())
     }

    fun onNewPassChanged(text: CharSequence?) {
        newPass.set(text.toString())
     }




    fun onResetPassClick(){

        if (pin.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter a valid pin sent on you registered email.", true));

        }else if (newPass.get().toString().isEmpty()){
            isError.postValue(AlertModel("Please set a valid new password.", true));
        }

        else{


            val map : HashMap<String, String>
                    = HashMap<String, String> ()
             map.put("otp",pin.get().toString())
             map.put("newPassword", newPass.get().toString())
             map.put("studentEmail",email.get().toString())

            Log.e("map",map.toString())
            isLoading.postValue(true)
            repoModel.resetPass(onDataReadyCallbackk,map,sessiontoken)

        }
    }



}