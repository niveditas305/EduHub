package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.commonResponse.CommonResponse
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.util.AlertModel


class ChangePasswordVM(var devicetoken:String): BaseViewModel(){
    val respData = MutableLiveData<CommonResponse>(null)


    val current = ObservableField<String>("")
    val newpass = ObservableField<String>("")
    val confirmnew = ObservableField<String>("")

    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as CommonResponse?
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






    fun changePassword(){

        if (current.get().toString().isEmpty()){

            isError.postValue(AlertModel("Please enter current password.",true));

        } else if (newpass.get().toString().isEmpty()){

            isError.postValue(AlertModel("Password is required. Please enter a valid password.",true))

        }else if (!confirmnew.get().toString().equals(newpass.get().toString())){
            isError.postValue(AlertModel("Password does not match. Please enter correct password",true))
        }

        else {

            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("currentPassword", current.get().toString())
            map.put("newpassword", confirmnew.get().toString())
            isLoading.postValue(true)
            repoModel.changePassword(onDataReadyCallback, devicetoken, map)
        }
    }



}