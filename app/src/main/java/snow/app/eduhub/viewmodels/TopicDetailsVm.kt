package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.chapterVideos.ChapterVideos
import snow.app.eduhub.ui.network.responses.getChapters.GetChaptersResponse
import snow.app.eduhub.ui.network.responses.topicdetails.TopicDetailsRes
import snow.app.eduhub.util.AlertModel


class TopicDetailsVm(var token: String) : BaseViewModel() {
    val respData = MutableLiveData<TopicDetailsRes>(null)
    val resprecentContinueTopic = MutableLiveData<BaseRes>(null)
    val subject_id = ObservableField<String>()
     val teacher_id = ObservableField<String>()
    val chapter_id = ObservableField<String>()
    val chaptername = ObservableField<String>()

    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as TopicDetailsRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData.postValue(d)


                } else {
                    respData.postValue(d)
                  //  isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }


    fun getTopicDetailswithpdfs() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("teacherId", teacher_id.get().toString())
        map.put("subjectId", subject_id.get().toString())
        map.put("chapterId", chapter_id.get().toString())
        isLoading.postValue(true)
        repoModel.topicDetailsRes(onDataReadyCallback, map, token)

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
    fun resprecentContinueTopic(chapterid: String, subjectid: String, topicid: String) {
        // isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("chapter_id", chapterid)
        map.put("subject_id", subjectid)
        map.put("topic_id", topicid)
        map.put("type", "2") //type 1 (recent topic), 2 (continue topic)


        repoModel.recentContinueTopic(callback_recentContinueTopic, map, token)
    }

}