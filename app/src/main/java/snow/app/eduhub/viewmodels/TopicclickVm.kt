package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.topicdetailsres.GetTopicDetailsRes
import snow.app.eduhub.ui.network.responses.topiclistres.TopicListRes
import snow.app.eduhub.util.AlertModel


class TopicclickVm(var token: String) : BaseViewModel() {
    val respData_topiclist = MutableLiveData<TopicListRes>(null)
    val respVideoData = MutableLiveData<GetTopicDetailsRes>(null)
    val resprecentContinueTopic = MutableLiveData<BaseRes>(null)
    val res_rating = MutableLiveData<BaseRes>(null)

    val subject_id = ObservableField<String>("")
    val rating = ObservableField<String>("0")
    val rating_value = ObservableField<String>("")
    val teacher_id = ObservableField<String>("")
    val chapter_id = ObservableField<String>("")
    val topic_id = ObservableField<String>("")
    val topic_name = ObservableField<String>("")
    val selected_topic = ObservableField<String>("")

    // interface call back for api call
    val callback_topiclist = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as TopicListRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData_topiclist.postValue(d)


                } else {
                    respData_topiclist.postValue(d)
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
    val callback_rating = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as BaseRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    res_rating.postValue(d)


                } else {
                    res_rating.postValue(d)
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
    val callback_topicdetails = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as GetTopicDetailsRes?
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


    fun getTopicDetails() {
        isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("teacherId", teacher_id.get().toString())
        map.put("subjectId", subject_id.get().toString())
        map.put("chapterId", chapter_id.get().toString())
        map.put("topicId", topic_id.get().toString())


        repoModel.getTopicDeatails(callback_topicdetails, map, token)
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

    fun getTopiclist() {
        isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("teacherId", teacher_id.get().toString())
        map.put("subjectId", subject_id.get().toString())
        map.put("chapterId", chapter_id.get().toString())
        repoModel.getTopiclist(callback_topiclist, map, token)

    }

    fun giveRating() {

        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("teacherId", teacher_id.get().toString())
        map.put("rating_star", subject_id.get().toString())
        isLoading.postValue(true)
        repoModel.giveRating(callback_rating, map, token)

    }
}