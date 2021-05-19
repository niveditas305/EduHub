package snow.app.eduhub.viewmodels

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.theartofdev.edmodo.cropper.CropImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import snow.app.eduhub.network.ApiAdapter
import snow.app.eduhub.network.ApiService
import snow.app.eduhub.network.responses.grades.GradesData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.fetchprofile.FetchProfile
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.util.AlertModel
import snow.app.eduhub.util.AppUtils
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class ProfileViewModel(var token: String) : BaseViewModel() {
    val respData = MutableLiveData<FetchProfile>(null)
    val respDataGrade = MutableLiveData<GradesData>()
    val respDataupdate = MutableLiveData<SignupRes>(null)
    var apiService = ApiAdapter().getRetrofitInstance()!!.create(ApiService::class.java)
    var first_name = ObservableField("")
    var last_name = ObservableField("")
    var email = ObservableField("")
    var number = ObservableField("")
    var classname = ObservableField("")
    var class_id = ObservableField("")
    var country_code = ObservableField("")
    var school = ObservableField("")
    var address = ObservableField("")
    var profile = ObservableField("")


    object DataBindingAdapter {
        @BindingAdapter("android:profile")
        @JvmStatic
        fun profile(imageView: ImageView, profilePic: String?) {
            Log.wtf("ffffffffffffffffffff", "$profilePic==")
            if (profilePic != null) {


                AppUtils.roundImageWithGlide(imageView, profilePic)
            }
        }
    }


    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as FetchProfile?
                if (d?.status!!) {
                    Log.e("status", "status");
                    respData.postValue(d)
                    first_name.set(d.data.firstName)
                    last_name.set(d.data.lastName)
                    email.set(d.data.email)
                    number.set(d.data.studentMoblie)
                    school.set(d.data.studentSchool)
                    country_code.set(d.data.country_code)


                    if (d.data.studentAddress == null || d.data.studentAddress.equals("null")) {

                        address.set("")
                    } else {
                        address.set(d.data.studentAddress)
                    }



                    classname.set(d.data.grade)

                    if (d.data.schoolClassId != 0) {
                        class_id.set(d.data.schoolClassId.toString())
                    }

                    profile.set(d.data.studentImage)


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

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri = result.uri
                profile.set(resultUri.getPath())
                //   AppUtils.roundImageWithGlide(imageView, profilePic)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Log.e("err", "--" + error)
            }
        }
    }

    fun getUserDetail() {

        isLoading.postValue(true)
        repoModel.getUserDetail(onDataReadyCallback, token)

    }

    fun getGrades() {
        repoModel.grades(onDataReadyGradeCallback)
    }

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

    //UPDATE profile
    fun update(ccp:String) {
        if (first_name.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter first name.", true));

        } else if (last_name.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter last name.", true));

        } else if (email.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter valid email.", true));

        } else if (number.get().toString().isEmpty() || number.get().toString().length < 10) {
            isError.postValue(AlertModel("Please enter valid phone number.", true));

        } else if (classname.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please select grade.", true));

        } else if (school.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter school name.", true));

        } else if (address.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter address.", true));

        } else {


            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("first_name", first_name.get().toString())
            map.put("last_name", last_name.get().toString())
            map.put("email", email.get().toString())
            map.put("student_mobile", number.get().toString())
            map.put("student_school", school.get().toString())
            map.put("school_class_id", class_id.get().toString())
            map.put("student_address", address.get().toString())

            map.put("country_code", ccp )
            Log.e("map", map.toString())
            isLoading.postValue(true)
            updateProfile(map, token)

        }
    }

    fun updateProfile(

        map: HashMap<String, String>, token: String
    ) {
        var profile_image: MultipartBody.Part? = null
        if (profile.get() != null && !profile.get().toString().isEmpty()) {
            if (!profile.get().toString().startsWith("http")) {
                var file: File = File(profile.get().toString())

                var requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
                profile_image =
                    MultipartBody.Part.createFormData("student_image", file.getName(), requestFile);
            }
        }
        val first_name =
            RequestBody.create(MediaType.parse("multipart/form-data"), map.get("first_name"))
        val last_name =
            RequestBody.create(MediaType.parse("multipart/form-data"), map.get("last_name"))
        val email =
            RequestBody.create(MediaType.parse("multipart/form-data"), map.get("email"))
        val student_mobile =
            RequestBody.create(MediaType.parse("multipart/form-data"), map.get("student_mobile"))
        val student_school =
            RequestBody.create(MediaType.parse("multipart/form-data"), map.get("student_school"))
        val school_class_id =
            RequestBody.create(MediaType.parse("multipart/form-data"), map.get("school_class_id"))
        val student_address =
            RequestBody.create(MediaType.parse("multipart/form-data"), map.get("student_address"))

        val country_code =
            RequestBody.create(MediaType.parse("multipart/form-data"), map.get("country_code"))


        apiService.updateProfile(
            token,
            first_name,
            last_name,
            email,
            student_mobile,
            student_school,
            school_class_id,
            student_address,
            country_code,
            profile_image
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : io.reactivex.Observer<SignupRes> {

                override fun onNext(data: SignupRes) {
                    isLoading.postValue(false)
                    respDataupdate.postValue(data)

                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.e("error i  upafte", "update-" + e.toString())
                    onDataReadyCallback.onDataReady(null, true)

                }
            })


    }

}