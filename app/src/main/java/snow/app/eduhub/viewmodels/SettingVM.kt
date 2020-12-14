package snow.app.eduhub.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.notification.SetNotificationStatus
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.util.AlertModel


class SettingVM(var token:String): BaseViewModel(){
    val respData = MutableLiveData<SetNotificationStatus>(null)




    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as SetNotificationStatus?
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


    fun changeNotificationStatus(setNotification:String){

        isLoading.postValue(true)
        val map : HashMap<String, String>
                = HashMap<String, String> ()
        map.put("setNotification",setNotification)

        repoModel.changeNotificationStatus(onDataReadyCallback,map,token)

    }
}