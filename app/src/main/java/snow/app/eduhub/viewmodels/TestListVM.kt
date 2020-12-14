package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.chapterVideos.ChapterVideos
import snow.app.eduhub.ui.network.responses.getChapters.GetChaptersResponse
import snow.app.eduhub.ui.network.responses.testlistres.TestListRes
import snow.app.eduhub.util.AlertModel


class TestListVM(var token: String) : BaseViewModel() {
    val respData = MutableLiveData<TestListRes>(null)

    val subject_id = ObservableField<String>("")

    val subjectname = ObservableField<String>("")


    // interface call back for api call
    val callback_fetchtestlist = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as TestListRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData.postValue(d)


                } else {
                    respData.postValue(d)
                 //   isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }

    fun testList() {

        isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()

         map.put("subject_id", subject_id.get().toString())
        repoModel.testList(callback_fetchtestlist, map, token)

    }



}