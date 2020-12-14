package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.forgotPassword.ForgotPasswordResponse
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.util.AlertModel


class ForgotPasswordVM(var token:String): BaseViewModel(){
    val respData = MutableLiveData<ForgotPasswordResponse>(null)
    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as ForgotPasswordResponse?
                if (d?.status!!){
                    Log.e("status","status");
                    respData.postValue(d)


                }else{
                    respData.postValue(d)
                    isError.postValue(AlertModel(d.message.toString(),true));
                    Log.e("status","e");
                }

            }else{
                isError.postValue(AlertModel("Something went wrong.",true));
                Log.e("status","ee");
            }

        }
    }





    fun forgotPassword(email:String){


        val map : HashMap<String, String>
                = HashMap<String, String> ()
        map.put("studentEmail",email)
        isLoading.postValue(true)
        repoModel.forgotPassword(onDataReadyCallback,map,token)

    }



}