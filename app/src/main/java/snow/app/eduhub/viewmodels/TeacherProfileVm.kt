package snow.app.eduhub.viewmodels

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.ui.network.responses.searchres.SearchRes
import snow.app.eduhub.ui.network.responses.teachersprofile.TeachersProfileRes
import snow.app.eduhub.util.AlertModel
import snow.app.eduhub.util.AppUtils


class TeacherProfileVm(var token: String) : BaseViewModel() {
    val res_teachersprofile = MutableLiveData<TeachersProfileRes>(null)

    val teacher_id = ObservableField<String>("")
    val profile = ObservableField<String>("")
    val name = ObservableField<String>("")
    val subjects = ObservableField<String>("")
    val classname = ObservableField<String>("")
    val experience = ObservableField<String>("")
    val rating = ObservableField<String>("")
    val rating_big = ObservableField<String>("")
    val about = ObservableField<String>("")
    val student_count = ObservableField<String>("")

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
                val d = data as TeachersProfileRes?
                if (d?.status!!) {
                    Log.e("status", "status");
                    res_teachersprofile.postValue(d)
                    name.set(d.data.teacherFirstName + " " + d.data.teacherLastName)
                    subjects.set(d.data.teacherSubject)
                    about.set(d.data.teacherDescription)
                    classname.set(d.data.className)
                    profile.set(d.data.teacherImage)
                    student_count.set("From "+d.data.reviewsCount + " students")
                    experience.set(d.data.teacherExpirence.toString() + " years experience")
                    if (d.data.teacherRating != null) {
                        rating.set("("+String.format("%.1f", d.data.teacherRating.toFloat()).toString() +")")
                        rating_big.set(String.format("%.1f", d.data.teacherRating.toFloat()).toString() )
                    } else {

                        rating.set(
                            ("(0.0)")
                        )
                        rating_big.set(
                            ("(0.0)")
                        )
                    }
                } else {
                    res_teachersprofile.postValue(d)
                    //  isError.postValue(AlertModel(d.message.toString(), true));
                    Log.e("status", "e");
                }

            } else {
                isError.postValue(AlertModel("Something went wrong.", true));
                Log.e("status", "ee");
            }

        }
    }


    fun teacherProfile() {


        val map: HashMap<String, String> = HashMap<String, String>()
        map.put("teacher_id", teacher_id.get().toString())
        isLoading.postValue(true)
        repoModel.teacherProfile(onDataReadyCallback, map, token)


    }


}