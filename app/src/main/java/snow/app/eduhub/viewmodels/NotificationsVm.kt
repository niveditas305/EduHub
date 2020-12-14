package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData

import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.fetchnotficationres.FetchNotificationListRes
import snow.app.eduhub.util.AlertModel


class NotificationsVm(var devicetoken:String) :  BaseViewModel() {
    val respDatanotification = MutableLiveData<FetchNotificationListRes>(null)



    // interface call back for api call
    val onDataReadyCallbackfetch = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
           isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as FetchNotificationListRes?
                if (d?.status!!){
                    Log.e("status","status");
                    respDatanotification.postValue(d)

                }else{
                    respDatanotification.postValue(d)
                    //  isError.postValue(AlertModel(d.message.toString(),true));
                    Log.e("status","e");
                }






            }else{
                   isError.postValue(AlertModel("Something went wrong.",true));
                Log.e("status","ee");
            }

        }
    }


    fun fetchAllNOtifications(){
        isLoading.postValue(true)
        val map : HashMap<String, String>
                = HashMap<String, String> ()

         repoModel.fetchAllNOtifications(onDataReadyCallbackfetch,devicetoken)
    }

}