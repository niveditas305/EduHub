package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.TeachersAdapter
import kotlinx.android.synthetic.main.activity_teacher_listing_screen.*
import snow.app.eduhub.databinding.ActivityLessonListingBinding
import snow.app.eduhub.databinding.ActivityTeacherListingScreenBinding
import snow.app.eduhub.ui.adapter.AllTeachersAdapter
import snow.app.eduhub.ui.adapter.LessonsAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.util.LikeDislikeListener
import snow.app.eduhub.util.OnTokenExpired
import snow.app.eduhub.viewmodels.AllTeachersVM
import snow.app.eduhub.viewmodels.LessonListVm

class LessonListingScreen : BaseActivity() {
    var viewModel: LessonListVm? = null
    lateinit var binding: ActivityLessonListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_teacher_listing_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson_listing)
        binding.lifecycleOwner = this
        viewModel = LessonListVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        if (intent.hasExtra("subjectid")) {
            viewModel?.subjectid?.set(intent.getStringExtra("subjectid"))
        }




      //clicks
        clicks()

        //observers
        observers()

    }


    fun observers() {

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


        //fetch teacher listing response
        viewModel?.resTeachers?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    val linearLayoutManager = LinearLayoutManager(this)
                    rv_teachers.layoutManager = linearLayoutManager
                    val adapter = LessonsAdapter(
                        this,
                        it.data,
                        viewModel?.subjectid?.get().toString()

                    )
                    rv_teachers.adapter = adapter

                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    showError(it.message, this)
                }
            }

        })


    }

    fun clicks() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isNetworkConnected()) {
            viewModel?.getLessonList()
        } else {
            showInternetToast()
        }
    }


}