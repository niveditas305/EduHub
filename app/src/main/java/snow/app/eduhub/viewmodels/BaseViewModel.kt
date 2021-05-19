package snow.app.eduhub.viewmodels

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.repo.Repo
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.util.AlertModel
import java.util.*
import kotlin.collections.HashMap

open class BaseViewModel(/*var devicetoken:String*/) : BaseObservable() {
    var repoModel: Repo = Repo()

    open val isError = MutableLiveData<AlertModel>(AlertModel("", false))
    val isLoading = MutableLiveData<Boolean>(false)
    val respDataa_fb = MutableLiveData<SignupRes>(null)
    val res_favunfav = MutableLiveData<BaseRes>(null)
    val respData_google = MutableLiveData<SignupRes>(null)
    fun googleLogin(
        email: String,
        firstname: String,
        lastname: String,
        image: String,
        mobile: String,
        id: String,
        devicetoken: String,ccp: String
    ) {

        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("email", email)
        map.put("first_name", firstname)
        map.put("last_name", lastname)
        map.put("image", image)
        map.put("student_mobile", mobile)
        map.put("facebook_id", "")
        map.put("google_id", id)
        map.put("country_code", ccp )
        map.put("device_token", devicetoken)
        map.put("time_zone", TimeZone.getDefault().id)
        Log.e("map", map.toString())
        isLoading.postValue(true)
        repoModel.socialLogin(onDataReadyCallbackkk, map)

    }

    fun facebookLogin(
        email: String,
        firstname: String,
        lastname: String,
        image: String,
        mobile: String,
        id: String,
        devicetoken: String,ccp:String
    ) {

        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("email", email)
        map.put("first_name", firstname)
        map.put("last_name", lastname)
        map.put("image", image)
        map.put("student_mobile", mobile)
        map.put("facebook_id", id)
        map.put("country_code", ccp )
        map.put("google_id", "")
        map.put("device_token", devicetoken)
        map.put("time_zone", TimeZone.getDefault().id)
        Log.e("map", map.toString())

        Log.e("map", map.toString())
        isLoading.postValue(true)

        repoModel.socialLogin(onCallback, map)

    }


    // fav // unfav // teachers
    fun favUnfavTeacher(
        token: String,
        type: String,
        teacher_id: String

    ) {


        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("teacher_id", teacher_id)
        map.put("type", type) //type 1(favourite),2 (unfavourite)
        Log.e("map", map.toString())
        isLoading.postValue(true)
        repoModel.favUbfavTeachers(onDataReadyCallback_fav_unfav, token, map)

    }


    // fav un fav callback
    val onDataReadyCallback_fav_unfav = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as BaseRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    res_favunfav.postValue(d)


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
    // interface call back for api call
    val onDataReadyCallbackkk = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as SignupRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData_google.postValue(d)


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

    // interface call back for api call
    val onCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as SignupRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respDataa_fb.postValue(d)


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
}