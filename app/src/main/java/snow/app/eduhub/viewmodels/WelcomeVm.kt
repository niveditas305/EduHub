package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.fetchWelcomeData.WelcomeData
import snow.app.eduhub.network.responses.getStarted.GetStartedData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.util.AlertModel


class WelcomeVm(): BaseViewModel(){
    val respData = MutableLiveData<WelcomeData>(null)
    val respDataStarted = MutableLiveData<GetStartedData>(null)
    var about   = ObservableField("")




    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as WelcomeData?
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

    // interface call back for api call
    val onDataReadyCallbackGetStarted = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as GetStartedData?
                if (d?.status!!){
                    Log.e("status","status");
                    respDataStarted.postValue(d)


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






    fun getWelcomeData( ){


        isLoading.postValue(true)

        repoModel.welcome(onDataReadyCallback)

    }


    fun getStartedData(){
        isLoading.postValue(true)

        repoModel.getStarted(onDataReadyCallbackGetStarted)
    }
}