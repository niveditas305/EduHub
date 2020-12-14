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
import snow.app.eduhub.ui.network.responses.getsubjectlistbyid.Data
import snow.app.eduhub.ui.network.responses.getsubjectlistbyid.GetSubjectListById
import snow.app.eduhub.ui.network.responses.homedatares.HomeDataRes
import snow.app.eduhub.util.AlertModel


class SubjectByIdVm(var token: String) : BaseViewModel() {

    val respDataSubjectsbyId = MutableLiveData<GetSubjectListById>(null)
    val teacher_id = ObservableField<String>("")
    val name = ObservableField<String>("")

     // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as GetSubjectListById?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respDataSubjectsbyId.postValue(d)


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






    fun getSubjectsByTearcherID() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("teacherId", teacher_id.get().toString())
        isLoading.postValue(true)
        repoModel.getSubjectsById(onDataReadyCallback, map,token)
    }



   /* fun getTopTeachers() {
        isLoading.postValue(true)
        repoModel.getTopTeachers(onTopTeachersDataReadyCallback, token)
    }*/

}