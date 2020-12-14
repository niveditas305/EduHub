package snow.app.eduhub

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import snow.app.eduhub.ui.adapter.TeachersAdapter
import kotlinx.android.synthetic.main.activity_teacher_listing_screen.*
import snow.app.eduhub.databinding.ActivityTeacherListingScreenBinding
import snow.app.eduhub.ui.adapter.SeeAllTeachersAdapter
import snow.app.eduhub.ui.network.responses.homedatares.TopTeacher
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.util.LikeDislikeListener
import snow.app.eduhub.util.OnTokenExpired
import snow.app.eduhub.viewmodels.AllTeachersVM
import java.lang.reflect.Type

class SeeAllTeacherListingScreen : BaseActivity(), LikeDislikeListener,
    OnTokenExpired.OnTokenExpiredListener {
    lateinit var teachersAdapter: TeachersAdapter
    var viewModel: AllTeachersVM? = null
    var list: ArrayList<TopTeacher> = ArrayList<TopTeacher>()
    lateinit var binding: ActivityTeacherListingScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_teacher_listing_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_listing_screen)
        binding.lifecycleOwner = this
        viewModel = AllTeachersVM(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel?.isLoading?.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel?.isError?.observe(this, Observer {
            if (it.isError) {
                showError(it.message, this);
            }

        })
        /*   if (intent.hasExtra("subjectname")) {
               viewModel?.subject?.set(intent.getStringExtra("subjectname"))
               viewModel?.subjectid?.set(intent.getStringExtra("subjectid"))
           }*/


        binding.ivBack.setOnClickListener {
            onBackPressed()
        }



        if (intent.hasExtra("salonlist")) {

            list.clear()
            dialog.show()
            val gson = Gson()
            val jsonDetails: String
            jsonDetails = intent.getSerializableExtra("salonlist").toString()
            val listOfdoctorType: Type = object : TypeToken<List<TopTeacher?>?>() {}.type
            list = gson.fromJson(jsonDetails, listOfdoctorType)
            //fetch teacher listing response
            val linearLayoutManager = LinearLayoutManager(this)
            rv_teachers.layoutManager = linearLayoutManager
            val adapter = SeeAllTeachersAdapter(
                this,
                list,/*    viewModel?.subjectid?.get().toString(),   viewModel?.subject?.get().toString(),*/
                this
            )
            rv_teachers.adapter = adapter

            // adapter.notifyDataSetChanged()
            dialog.dismiss()

        }

        viewModel?.res_favunfav?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    showToast(it.message)
                } else {
                    dialog.dismiss()
                    Log.e("statusfalse", "login--")
                    showToast(it.message)
                }
            }

        })
/*
        val linearLayoutManager = LinearLayoutManager(this)
        rv_teachers.layoutManager = linearLayoutManager
        teachersAdapter = TeachersAdapter(this)
        rv_teachers.adapter = teachersAdapter
*/
    }

    /*  override fun onResume() {
          super.onResume()
       *//*   if (isNetworkConnected()) {
            viewModel?.fetchTeachers()
        } else {
            showInternetToast()
        }*//*
    }
*/

    override fun likeClick(
        id: String,
        type: String,
        imageView_fav: ImageView,
        imageView_unfav: ImageView
    ) {


        if (isNetworkConnected()){

            viewModel!!.favUnfavTeacher(getUserToken(), type, id)
        }else{
            showInternetToast()
        }

    }


    override fun onTokenExpiredListener() {
        viewModel!!.isLoading.postValue(false)
        showTokenError()
    }
}