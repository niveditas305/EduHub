package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.chapterVideos.ChapterVideos
import snow.app.eduhub.ui.network.responses.getChapters.GetChaptersResponse
import snow.app.eduhub.util.AlertModel


class ChaptersVM(var token: String) : BaseViewModel() {
    val respData = MutableLiveData<GetChaptersResponse>(null)
    val respVideoData = MutableLiveData<ChapterVideos>(null)
    val resprecentContinueTopic = MutableLiveData<BaseRes>(null)
    val subject_id = ObservableField<String>("")
    val con_subject_id = ObservableField<String>("")
    val topic_id = ObservableField<String>("")
    val con_topic_id = ObservableField<String>("")
    val continue_topic = ObservableField<String>("")
    val teacher_id = ObservableField<String>("")
    val con_teacher_id = ObservableField<String>("")
    val chapter_id = ObservableField<String>("")
    val con_chapter_id = ObservableField<String>("")
    val subjectname = ObservableField<String>("")
    val selected_chapter = ObservableField<String>("")




      // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as GetChaptersResponse?
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

    // interface call back for api call
    val onVideoDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as ChapterVideos?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respVideoData.postValue(d)


                } else {
                    respVideoData.postValue(d)
                 //   isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
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
    fun resprecentContinueTopic(chapterid: String, subjectid: String, topicid: String) {
        // isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("chapter_id", chapterid)
        map.put("subject_id", subjectid)
        map.put("topic_id", topicid)
        map.put("type", "2") //type 1 (recent topic), 2 (continue topic)


        repoModel.recentContinueTopic(callback_recentContinueTopic, map, token)
    }
    fun getChapters() {

        isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()

        map.put("teacherId", teacher_id.get().toString())
        map.put("subjectId", subject_id.get().toString())
        repoModel.getChapters(onDataReadyCallback, map, token)

    }

    fun getChapterVideos() {

        isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()

        map.put("teacherId", teacher_id.get().toString())
        map.put("subjectId", subject_id.get().toString())
        map.put("chapter_id", chapter_id.get().toString())
        repoModel.getChapterRelatedVideos(onVideoDataReadyCallback, map, token)

    }


}