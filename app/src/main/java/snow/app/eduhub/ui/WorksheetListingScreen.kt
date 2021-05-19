package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_teacher_listing_screen.*
import snow.app.eduhub.databinding.ActivityLessonListingBinding
import snow.app.eduhub.databinding.ActivityTeacherListingScreenBinding
import snow.app.eduhub.databinding.ActivityWorksheetListingBinding
import snow.app.eduhub.ui.adapter.*
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.util.LikeDislikeListener
import snow.app.eduhub.util.OnTokenExpired
import snow.app.eduhub.viewmodels.AllTeachersVM
import snow.app.eduhub.viewmodels.LessonListVm
import snow.app.eduhub.viewmodels.WorksheetListVm

class WorksheetListingScreen : BaseActivity() {
    var viewModel: WorksheetListVm? = null
    lateinit var binding: ActivityWorksheetListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_teacher_listing_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_worksheet_listing)
        binding.lifecycleOwner = this
        viewModel = WorksheetListVm(getUserToken())
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
                    binding.rvTeachers.layoutManager = linearLayoutManager
                    val adapter = WorksheetListAdapter(
                        this,
                        it.data

                    )
                    binding.rvTeachers.adapter = adapter
                    binding.noRecordFound.visibility = View.GONE
                    binding.rvTeachers.visibility = View.VISIBLE
                } else {


                    binding.noRecordFound.visibility = View.VISIBLE
                    binding.rvTeachers.visibility = View.GONE
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                  //  showError(it.message, this)
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
            viewModel?.getWorksheetList()
        } else {
            showInternetToast()
        }
    }


}