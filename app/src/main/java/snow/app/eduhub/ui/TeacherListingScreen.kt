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
import snow.app.eduhub.databinding.ActivityTeacherListingScreenBinding
import snow.app.eduhub.ui.adapter.AllTeachersAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.util.LikeDislikeListener
import snow.app.eduhub.util.OnTokenExpired
import snow.app.eduhub.viewmodels.AllTeachersVM

class TeacherListingScreen : BaseActivity(), LikeDislikeListener,
    OnTokenExpired.OnTokenExpiredListener {
    lateinit var teachersAdapter: TeachersAdapter
    var viewModel: AllTeachersVM? = null
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
        if (intent.hasExtra("subjectid")) {
            viewModel?.subjectid?.set(intent.getStringExtra("subjectid"))
            viewModel?.subject?.set(intent.getStringExtra("subjectname"))
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }


        //fetch teacher listing response
        viewModel?.resTeachers?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    val linearLayoutManager = LinearLayoutManager(this)
                    binding.rvTeachers.layoutManager = linearLayoutManager
                    val adapter = AllTeachersAdapter(
                        this,
                        it.data,
                        viewModel?.subjectid?.get().toString(),
                        viewModel?.subject?.get().toString(),
                        this
                    )
                    binding.rvTeachers.adapter = adapter

                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    showError(it.message, this)
                }
            }

        })
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

    override fun onResume() {
        super.onResume()
        if (isNetworkConnected()) {
            viewModel?.fetchTeachers()
        } else {
            showInternetToast()
        }
    }


    override fun likeClick(
        id: String,
        type: String,
        imageView_fav: ImageView,
        imageView_unfav: ImageView
    ) {


        if (isNetworkConnected()) {
            viewModel!!.favUnfavTeacher(getUserToken(), type, id)
        } else {
            showInternetToast()
        }


    }


    override fun onTokenExpiredListener() {
        viewModel!!.isLoading.postValue(false)

        showTokenError()
    }
}