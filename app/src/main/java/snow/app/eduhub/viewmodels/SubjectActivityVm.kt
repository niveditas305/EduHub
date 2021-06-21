package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.getSubjects.GetSubjectByGrade
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.getstudyguild.StudyGuideRes
import snow.app.eduhub.ui.network.responses.subjectsres.SubjectRes
import snow.app.eduhub.util.AlertModel
import kotlin.collections.HashMap


class SubjectActivityVm(var devicetoken:String) : BaseViewModel() {
    val respData = MutableLiveData<GetSubjectByGrade>(null)


    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as GetSubjectByGrade?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData.postValue(d)
                } else {
                    respData.postValue(d)
                    //  isError.postValue(AlertModel(d.message.toString(),true));
                    Log.e("status", "e");
                }
            } else {
                isError.postValue(AlertModel("Something went wrong.", true));

            }

        }
    }


    fun getSubjectByClass(class_id: String) {
        var map: HashMap<String, String> = HashMap<String, String>()
         map.put("class_id",class_id)
         isLoading.postValue(true)
        repoModel.getSubjectByClass(onDataReadyCallback,devicetoken,map)

    }


}