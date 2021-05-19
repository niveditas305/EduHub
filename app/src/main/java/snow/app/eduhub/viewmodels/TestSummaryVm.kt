package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.topTeachers.TopTeachers
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.fetchteachers.FetchTeachersRes
import snow.app.eduhub.ui.network.responses.testsummaryres.TestSummaryRes
import snow.app.eduhub.util.AlertModel

class TestSummaryVm(var token: String) : BaseViewModel() {
    val resSummary = MutableLiveData<TestSummaryRes>(null)
    var test_id = ObservableField("")
    var sol_pdf = ObservableField("")
    var test_unique_id = ObservableField("")
    val onTopTeachersDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as TestSummaryRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    resSummary.postValue(d)


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


    fun fetchSummary() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("test_id", test_id.get().toString())
        map.put("test_unique_id", test_unique_id.get().toString())
        isLoading.postValue(true)
        repoModel.testSummary(onTopTeachersDataReadyCallback, map, token)
    }

}