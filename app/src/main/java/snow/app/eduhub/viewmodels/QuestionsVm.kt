package snow.app.eduhub.viewmodels

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.chapterVideos.ChapterVideos
import snow.app.eduhub.ui.network.responses.getChapters.GetChaptersResponse
import snow.app.eduhub.ui.network.responses.getuniqueid.GetUniqueId
import snow.app.eduhub.ui.network.responses.submitans.SubmitAnsRes
import snow.app.eduhub.ui.network.responses.testquestionsres.TestQuestionsRes
import snow.app.eduhub.util.AlertModel


class QuestionsVm(var token: String) : BaseViewModel() {
    val respData = MutableLiveData<TestQuestionsRes>(null)
    val respData_submitans = MutableLiveData<SubmitAnsRes>(null)
    val respData_getUniqueId = MutableLiveData<GetUniqueId>(null)
    val test_id = ObservableField<String>("")
    val testname = ObservableField<String>("")
    val api_solution = ObservableField<String>("")
    val question_id = ObservableField<String>("")
    val subject_id = ObservableField<String>("")
    val test_unique_id = ObservableField<String>("")
    val attempt_status = ObservableField<String>("")
    val choose_option = ObservableField<String>("")
    val teacherId = ObservableField<String>("")
    val ques_count_value = ObservableField<String>("")
    val last_pos_rv = ObservableField<String>("")
   // val rv_item_pos = ObservableField<Int>(0)


    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as TestQuestionsRes?
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
    val callback_submitans = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as SubmitAnsRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData_submitans.postValue(d)


                } else {
                    respData_submitans.postValue(d)
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
    val callback_getUniqueId = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
             Log.e("callback", "callback");
            if (data != null) {
                val d = data as GetUniqueId?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData_getUniqueId.postValue(d)


                } else {
                    respData_getUniqueId.postValue(d)
                    //  isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }


    fun testQuestionsList() {

        isLoading.postValue(true)
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("test_id", test_id.get().toString())
        repoModel.testQuestionsList(onDataReadyCallback, map, token)

    }

    fun submitAns(view: View) {


            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("test_id", test_id.get().toString())
            map.put("question_id", question_id.get().toString())
            map.put("subject_id", subject_id.get().toString())
            map.put("test_unique_id", test_unique_id.get().toString())


            if (choose_option.get().toString().isEmpty()) {

                attempt_status.set("0")
                map.put("attempt_status", attempt_status.get().toString()) //1  (attempt), 0 (unattempt)
            } else {
                attempt_status.set("1")
                map.put("attempt_status", attempt_status.get().toString()) //1  (attempt), 0 (unattempt)
            }


            map.put("choose_option", choose_option.get().toString())
            isLoading.postValue(true)
            repoModel.submitAns(callback_submitans, map, token)





    }
    fun getUniqueId() {


            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("test_id", test_id.get().toString())
             repoModel.getUniqueId(callback_getUniqueId, map, token)





    }


}