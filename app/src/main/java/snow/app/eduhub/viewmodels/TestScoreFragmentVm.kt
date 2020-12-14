package snow.app.eduhub.viewmodels

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.grades.GradesData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.scoreres.ScoreRes
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.util.AlertModel


class TestScoreFragmentVm(var devicetoken: String) : BaseViewModel() {
    val respData = MutableLiveData<ScoreRes>()
     var start_date = ObservableField("")
    var end_date = ObservableField("")

    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as ScoreRes?
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





    fun testScore(startDate:String,endDate:String) {


        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("start_date", startDate)
        map.put("end_date", endDate)
        Log.e("map", map.toString())
        isLoading.postValue(true)
        repoModel.testScore(onDataReadyCallback,map,devicetoken)
    }





}