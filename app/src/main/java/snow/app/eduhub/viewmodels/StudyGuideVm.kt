package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.getstudyguild.StudyGuideRes
import snow.app.eduhub.util.AlertModel
import kotlin.collections.HashMap


class StudyGuideVm(var devicetoken:String) : BaseViewModel() {
    val respData = MutableLiveData<StudyGuideRes>(null)


    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as StudyGuideRes?
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


    fun getStudyGuide(class_id: String,subject_id :String,search_key :String) {
        var map: HashMap<String, String> = HashMap<String, String>()
        map.put("search_key",search_key)
        map.put("class_id",class_id)
        map.put("subject_id",subject_id )
        isLoading.postValue(true)
        repoModel.getStudyGuide(onDataReadyCallback,devicetoken,map)

    }


}