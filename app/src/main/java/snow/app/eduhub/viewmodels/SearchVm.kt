package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.searchres.SearchRes
import snow.app.eduhub.util.AlertModel


class SearchVm(var token: String) : BaseViewModel() {
    val res_subjects = MutableLiveData<SearchRes>(null)

    val search_type = ObservableField<String>("1")
    val keyword_ = ObservableField<String>()
    val selected_hint = ObservableField<String>("Search Subjects")
    val selected_type = ObservableField<String>("Subjects")

    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as SearchRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    res_subjects.postValue(d)


                } else {
                    res_subjects.postValue(d)
                    //  isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }



    fun searchApi() {


        val map: HashMap<String, String> = HashMap<String, String>()


        if (keyword_.get().toString().isEmpty()){
            isError.postValue(AlertModel("Please enter search keyword !", true));
        }else{
            map.put("search_key", keyword_.get().toString())
            map.put("search_type", search_type.get().toString())
            isLoading.postValue(true)
            repoModel.searchApi(onDataReadyCallback, map, token)
        }


    }


}