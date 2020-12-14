package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.topTeachers.TopTeachers
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.fetchteachers.FetchTeachersRes
import snow.app.eduhub.util.AlertModel

class AllTeachersVM(var token: String) : BaseViewModel() {
    val resTeachers = MutableLiveData<FetchTeachersRes>(null)

    var subject = ObservableField("")
    var subjectid = ObservableField("")
    val onTopTeachersDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as FetchTeachersRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    resTeachers.postValue(d)


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


    fun fetchTeachers() {
        val map: HashMap<String, String> = HashMap<String, String>()


        map.put("subject", subject.get().toString())
        isLoading.postValue(true)
        repoModel.getTopTeachers(onTopTeachersDataReadyCallback, token,map)
    }

}