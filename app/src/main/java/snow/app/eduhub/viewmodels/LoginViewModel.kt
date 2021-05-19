package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.util.AlertModel
import java.util.*
import kotlin.collections.HashMap


class LoginViewModel(var devicetoken:String): BaseViewModel(){
    val respData = MutableLiveData<SignupRes>(null)




    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                 val d=data as SignupRes?
                if (d?.status!!){
                    Log.e("status","status");
                    respData.postValue(d)


                }else{
                    respData.postValue(d)
                //    isError.postValue(AlertModel(d.message.toString(),true));
                    Log.e("status","e");
                }






            }else{
                isError.postValue(AlertModel("Something went wrong.",true));
                Log.e("status","ee");
            }

        }
    }






    fun loginUser(email:String,password:String){


        val map : HashMap<String, String>
                = HashMap<String, String> ()


        map.put("email",email)
        map.put("password",password)
        map.put("deviceToken",devicetoken)
        map.put("time_zone", TimeZone.getDefault().id)
        isLoading.postValue(true)
        repoModel.login(onDataReadyCallback,map)

    }



}