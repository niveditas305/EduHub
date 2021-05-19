package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData

import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.util.AlertModel


class ChangeEmailVm(var devicetoken: String) : BaseViewModel() {
    val respDatasendotp = MutableLiveData<BaseRes>(null)

    var email = ObservableField("")


    // interface call back for api call
    val onDataReadyCallbacksendOtp = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as BaseRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respDatasendotp.postValue(d)


                } else {
                    isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }


            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }


    fun onEmailTextChanged(text: CharSequence?) {
        email.set(text.toString())
    }


    fun sendOtp() {


        if (email.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter valid email", true))
        } else {


            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("email", email.get().toString())

            Log.e("map", map.toString())
            isLoading.postValue(true)
            repoModel.sendOtpOnemail(onDataReadyCallbacksendOtp, map, devicetoken)

        }
    }
}