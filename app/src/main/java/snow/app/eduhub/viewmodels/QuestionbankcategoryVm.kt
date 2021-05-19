package snow.app.eduhub.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.network.responses.TermsAndConditionsResponse
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.fetchquestionpprcat.FetchQuestionPprCategoryRes
import snow.app.eduhub.util.AlertModel


class QuestionbankcategoryVm(var devicetoken :String): BaseViewModel(){
    val respData = MutableLiveData<FetchQuestionPprCategoryRes>(null)




    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback","callback");
            if (data!=null){
                val d=data as FetchQuestionPprCategoryRes?
                if (d?.status!!){
                    Log.e("status","status");
                    respData.postValue(d)


                } else{

                    respData.postValue(d)
                   // isError.postValue(AlertModel(d.message.toString(),true));
                    Log.e("status","e");
                }

            } else{
               // isError.postValue(AlertModel("Something went wrong.",true));
                Log.e("status","ee");
            }

        }
    }






    fun fetchQuestionPprCat(){
        isLoading.postValue(true)
        repoModel.fetchQuestionPprCat(onDataReadyCallback,devicetoken)

    }

}