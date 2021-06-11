package snow.app.eduhub.viewmodels

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.grades.GradesData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.util.AlertModel
import java.util.*
import kotlin.collections.HashMap


class SelectGradeVm(var devicetoken: String) : BaseViewModel() {
     val respDataGrade = MutableLiveData<GradesData>()
    var gradeid = ObservableField("")
    var grade = ObservableField("")
    // interface call back for api call
    val onDataReadyGradeCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as GradesData?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respDataGrade.postValue(d)

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


    fun getGrades() {
        isLoading.postValue(true)
        repoModel.grades(onDataReadyGradeCallback)
    }
}