package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.topTeachers.TopTeachers
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.fetchteachers.FetchTeachersRes
import snow.app.eduhub.ui.network.responses.lessonlistres.LessonListRes
import snow.app.eduhub.util.AlertModel

class LessonListVm(var token: String) : BaseViewModel() {
    val resTeachers = MutableLiveData<LessonListRes>(null)

     var subjectid = ObservableField("")
    val lessonListCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as LessonListRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    resTeachers.postValue(d)


                } else {
                    resTeachers.postValue(d)
                  //  isError.postValue(AlertModel(d.message.toString(), true));
                 }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
             }

        }
    }


    fun getLessonList() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("subject_id", subjectid.get().toString())
        isLoading.postValue(true)
        repoModel.getLessonList(lessonListCallback,map, token)
    }

}