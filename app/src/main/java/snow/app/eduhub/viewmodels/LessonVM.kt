package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.getSubjects.GetSubjectByGrade
import snow.app.eduhub.network.responses.homeBanner.HomeBannerData
import snow.app.eduhub.network.responses.topPicks.TopPicks
import snow.app.eduhub.network.responses.topTeachers.TopTeachers
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.fetchsubjectlist.SubjectList
import snow.app.eduhub.ui.network.responses.homedatares.HomeDataRes
import snow.app.eduhub.util.AlertModel


class LessonVM(var token: String) : BaseViewModel() {

    val respDataSubjects = MutableLiveData<SubjectList>(null)


     // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as SubjectList?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respDataSubjects.postValue(d)


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






    fun getSubjects() {
        isLoading.postValue(true)
        repoModel.getSubjectList(onDataReadyCallback, token)
    }



   /* fun getTopTeachers() {
        isLoading.postValue(true)
        repoModel.getTopTeachers(onTopTeachersDataReadyCallback, token)
    }*/

}