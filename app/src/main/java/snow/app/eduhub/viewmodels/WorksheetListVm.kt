package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.topTeachers.TopTeachers
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.fetchteachers.FetchTeachersRes
import snow.app.eduhub.ui.network.responses.lessonlistres.LessonListRes
import snow.app.eduhub.ui.network.responses.worksheetlist.WorksheetListRes
import snow.app.eduhub.util.AlertModel

class WorksheetListVm(var token: String) : BaseViewModel() {
    val resTeachers = MutableLiveData<WorksheetListRes>(null)
    val resprecentContinueTopic = MutableLiveData<BaseRes>(null)
     var subjectid = ObservableField("")
    val lessonListCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as WorksheetListRes?
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

    // interface call back for api call
    val callback_recentContinueTopic = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            //  isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as BaseRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    resprecentContinueTopic.postValue(d)


                } else {
                    resprecentContinueTopic.postValue(d)
                    //   isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }

            } else {
                //  isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }
    fun resprecentContinueTopic(chapterid: String, subjectid: String, topicid: String,type:String) {
        // isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("chapter_id", chapterid)
        map.put("subject_id", subjectid)
        map.put("topic_id", topicid)
        map.put("type", type) //type 1 (recent topic), 2 (continue topic)


        repoModel.recentContinueTopic(callback_recentContinueTopic, map, token)
    }
    fun getWorksheetList() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("subject_id", subjectid.get().toString())
        isLoading.postValue(true)
        repoModel.getWorksheetList(lessonListCallback,map, token)
    }

}