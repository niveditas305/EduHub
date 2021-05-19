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


class SignupVm(var devicetoken: String) : BaseViewModel() {
    val respData = MutableLiveData<SignupRes>()
    val respDataGrade = MutableLiveData<GradesData>()
    var name = ObservableField("")
    var lastname = ObservableField("")
    var email = ObservableField("")
    var phoneno = ObservableField("")
    var pass = ObservableField("")
    var repass = ObservableField("")
    var term_check = ObservableField("0")
    var school = ObservableField("")
    var grade = ObservableField("")
    var gradeid = ObservableField("")

    // interface call back for api call
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as SignupRes?
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

    fun onNameTextChanged(text: CharSequence?) {
        name.set(text.toString())
    }

    fun onLastTextChanged(text: CharSequence?) {
        lastname.set(text.toString())
    }

    fun onEmailTextChanged(text: CharSequence?) {
        email.set(text.toString())
    }

    fun onPhonenoTextChanged(text: CharSequence?) {
        phoneno.set(text.toString())
    }

    fun onPasswordTextChanged(text: CharSequence?) {

        pass.set(text.toString())
    }

    fun onRePasswordTextChanged(text: CharSequence?) {

        repass.set(text.toString())
    }

    fun onGradeTextChanged(text: CharSequence?) {

        grade.set(text.toString())
    }

    fun onSchoolTextChanged(text: CharSequence?) {

        school.set(text.toString())
    }

    fun validateEmail(): Boolean {
        return !TextUtils.isEmpty(
            email.get().toString()
        ) && Patterns.EMAIL_ADDRESS.matcher(email.get().toString()).matches()
    }

    fun getGrades() {
        repoModel.grades(onDataReadyGradeCallback)
    }

    fun onSignupClick(ccp: String) {
        val map: HashMap<String, String> = HashMap<String, String>()



        if (name.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter first name.", true));
        } else if (lastname.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter last name.", true));
        } else if (!validateEmail()) {
            isError.postValue(AlertModel("Please enter valid email.", true));
        } else if (phoneno.get().toString().isEmpty() || phoneno.get()?.length!! < 10) {
            isError.postValue(AlertModel("Please enter valid phone number.", true));
        } else if (school.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please enter school name.", true));

        } else if (grade.get().toString().isEmpty()) {
            isError.postValue(AlertModel("Please select grade.", true));
        } else if (pass.get().toString().isEmpty() || pass.get()?.length!! < 8) {
            isError.postValue(AlertModel("Please enter valid 8 digit password.", true));
        } else if (repass.get().toString().isEmpty() || repass.get()?.length!! < 8) {
            isError.postValue(AlertModel("Please re-enter password.", true));
        } else {


            if (!repass.get().toString().equals(pass.get().toString())) {
                isError.postValue(
                    AlertModel(
                        "Password and Re-enter password does not match. Please enter correct password",
                        true
                    )
                )
            } else {
                map.put("last_name", lastname.get().toString())
                map.put("first_name", name.get().toString())
                map.put("email", email.get().toString())
                map.put("student_moblie",  phoneno.get().toString())
                map.put("studentSchool", school.get().toString())
                map.put("password", pass.get().toString())
                map.put("studentGrade", gradeid.get().toString())
                map.put("deviceToken", devicetoken)
                map.put("country_code", ccp )
                map.put("reg_status", "1")
                map.put("time_zone", TimeZone.getDefault().id)
                Log.e("map", map.toString())
                isLoading.postValue(true)
                repoModel.register(onDataReadyCallback, map)
            }

        }

    }


    fun googleLogin(email: String, name: String, image: String, id: String) {

        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("email", email)
        map.put("name", name)
        map.put("image", image)
        map.put("user_type", "2")
        map.put("social_login_id", id)
        map.put("social_login_type", "1")
        /*   map.put("is_term_accept",term_check.get().toString())*/
        map.put("device_token", devicetoken)

        Log.e("map", map.toString())
        isLoading.postValue(true)
        // repoModel.socialLogin(onDataReadyCallback,map)

    }


}