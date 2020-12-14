package snow.app.eduhub.viewmodels


import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.commonResponse.CommonResponse
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.admindetailsres.FetchAdminDetails
import snow.app.eduhub.util.AlertModel


class ContactUsVM(var token: String) : BaseViewModel() {
    val respData = MutableLiveData<CommonResponse>(null)
    val respDatafetchdetails = MutableLiveData<FetchAdminDetails>(null)

    val phonenumber = ObservableField<String>("")
    val fullName = ObservableField<String>("")
    val email = ObservableField<String>("")
    val message = ObservableField<String>("")
    val time = ObservableField<String>("")


    // interface call back for api call
    val onDataReadyCallback_fetch = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as FetchAdminDetails?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respDatafetchdetails.postValue(d)
                    phonenumber.set(d.data.phoneNumber)
                    time.set(d.data.contactTime)

                } else {
                    respDatafetchdetails.postValue(d)
                    //   isError.postValue(AlertModel(d.message.toString(),true));
                    Log.e("status", "e");
                }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }


    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as CommonResponse?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData.postValue(d)


                } else {
                    respData.postValue(d)
                   // isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }


    fun getContactUsData() {


        if (fullName.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter your name", true));
        } else if (email.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter your email", true));

        } else if (message.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter message", true));

        } else {
            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("fullName", fullName.get().toString())
            map.put("email", email.get().toString())
            map.put("message", message.get().toString())
            isLoading.postValue(true)
            repoModel.fetchContactUsData(onDataReadyCallback, map, token)
        }


    }

    fun fetchAdminDetails() {

        isLoading.postValue(true)
        repoModel.fetchAdminDetails(onDataReadyCallback_fetch, token)

    }


}