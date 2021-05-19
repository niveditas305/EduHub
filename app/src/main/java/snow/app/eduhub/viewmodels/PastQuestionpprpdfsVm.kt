package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.forgotpassres.ForgotPassRes
import snow.app.eduhub.ui.network.responses.pastquestions.PastQuestionPpr
import snow.app.eduhub.util.AlertModel


class PastQuestionpprpdfsVm(var sessiontoken:String):BaseViewModel( ){
    val respData = MutableLiveData<PastQuestionPpr>(null)
    var past_question_category_id = ObservableField("")
    var past_question_category_name = ObservableField("")
      var subject_id = ObservableField("")




    // interface call back for api call
    val onDataReadyCallbackk = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as PastQuestionPpr?
                if (d?.status!!){
                    Log.e("status","status");
                    respData.postValue(d)


                }else{
                    respData.postValue(d)
                   // isError.postValue(AlertModel(d.message.toString(),true));
                    Log.e("status","e");
                }






            }else{
                isError.postValue(AlertModel("Something went wrong.",true));
                Log.e("status","ee");
            }

        }
    }








    fun fetchPastQuestionpprs(){



            val map : HashMap<String, String>
                    = HashMap<String, String> ()
             map.put("past_question_category_id", past_question_category_id.get().toString())
             map.put("subject_id",subject_id.get().toString())

            Log.e("map",map.toString())
            isLoading.postValue(true)
            repoModel.fetchPastQuestionpprs(onDataReadyCallbackk,sessiontoken,map)

    }



}