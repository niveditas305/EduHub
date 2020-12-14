package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.otpVerification.VerifyOTP
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.util.AlertModel


class VerificationVM(var devicetoken: String) : BaseViewModel() {
    val respData = MutableLiveData<VerifyOTP>(null)

    val respDatasendotp = MutableLiveData<BaseRes>(null)
    var email = ObservableField("")

    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as VerifyOTP?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData.postValue(d)


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


    fun verifyOTP(otp: String) {

        isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("mobileOtp", otp)
        repoModel.verifyOTP(onDataReadyCallback, devicetoken, map)

    }


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

    fun sendOtp() {


        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("email", email.get().toString())

        Log.e("map", map.toString())
        isLoading.postValue(true)
        repoModel.sendOtpOnemail(onDataReadyCallbacksendOtp, map, devicetoken)


    }


}