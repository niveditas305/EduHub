package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.fetchfavteachers.FetchFavTeacherRes
import snow.app.eduhub.util.AlertModel

class FavTeachersVm(var token: String) : BaseViewModel() {
    val resTeachers = MutableLiveData<FetchFavTeacherRes>(null)

     val onTopTeachersDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as FetchFavTeacherRes?
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


    fun fetchfavTeachers() {
        isLoading.postValue(true)
        repoModel.fetchFavTeachers(onTopTeachersDataReadyCallback, token)
    }

}