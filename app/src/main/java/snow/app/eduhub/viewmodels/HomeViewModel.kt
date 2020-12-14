package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.getSubjects.GetSubjectByGrade
import snow.app.eduhub.network.responses.homeBanner.HomeBannerData
import snow.app.eduhub.network.responses.topPicks.TopPicks
import snow.app.eduhub.network.responses.topTeachers.TopTeachers
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.homedatares.HomeDataRes
import snow.app.eduhub.util.AlertModel


class HomeViewModel(var token: String) : BaseViewModel() {
    val respData = MutableLiveData<HomeBannerData>(null)
    val reshome = MutableLiveData<HomeDataRes>()
    val resprecentContinueTopic = MutableLiveData<BaseRes>(null)
    val respDataSubjects = MutableLiveData<GetSubjectByGrade>(null)

    val respDataTopPicks = MutableLiveData<TopPicks>(null)

    val respDataTopTeachers = MutableLiveData<TopTeachers>(null)
    var recently_learned = ObservableField("")
    var countinue_topic = ObservableField("")
    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as HomeBannerData?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData.postValue(d)


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

    // fetch home data response together
    val callback_homedata = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as HomeDataRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    reshome.postValue(d)


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

    // interface call back for api call
    val onSubjectsDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as GetSubjectByGrade?
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

    // interface call back for api call
    val onTopPicksDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as TopPicks?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respDataTopPicks.postValue(d)


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


    // interface call back for api call
    val onTopTeachersDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as TopTeachers?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respDataTopTeachers.postValue(d)


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

    fun getBanners() {
        isLoading.postValue(true)
        repoModel.getBanners(onDataReadyCallback)

    }

    fun getHomeData() {
        isLoading.postValue(true)
        repoModel.fetchHomeDataApi(callback_homedata, token)

    }

    fun getSubjects() {
        isLoading.postValue(true)
        repoModel.getSubjectByGrade(onSubjectsDataReadyCallback, token)
    }


    fun getTopPicks() {
        isLoading.postValue(true)
        repoModel.getTopPicks(onTopPicksDataReadyCallback, token)
    }

   /* fun getTopTeachers() {
        isLoading.postValue(true)
        repoModel.getTopTeachers(onTopTeachersDataReadyCallback, token)
    }*/

}