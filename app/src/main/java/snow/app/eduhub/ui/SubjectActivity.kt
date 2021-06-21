package snow.app.eduhub.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivitySelectGradeBinding
import snow.app.eduhub.databinding.ActivitySubjectBinding
import snow.app.eduhub.ui.adapter.SubjectsAdapter
import snow.app.eduhub.ui.adapter.SubjectsClassidAdapter
import snow.app.eduhub.ui.network.responses.homedatares.Subject
import snow.app.eduhub.ui.network.responses.subjectsres.Data
import snow.app.eduhub.util.AppSession
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.SelectGradeVm
import snow.app.eduhub.viewmodels.SignupVm
import snow.app.eduhub.viewmodels.SubjectActivityVm

class SubjectActivity : BaseActivity() {
    lateinit var binding: ActivitySubjectBinding
    lateinit var viewModel: SubjectActivityVm
    var subjectlist: ArrayList<snow.app.eduhub.network.responses.getSubjects.Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subject)
        binding.lifecycleOwner = this
        viewModel = SubjectActivityVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        observers()
        if (intent.hasExtra("gradeid")) {
            if (isNetworkConnected()) {
                viewModel.getSubjectByClass(intent.getStringExtra("gradeid").toString())
            } else {
                showInternetToast()
            }
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }


    fun observers() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel.isError.observe(this, Observer {
            if (it.isError) {
                showError(it.message, this);
            }

        })


        viewModel.respData.observe(this, Observer {
            if (it != null) {
                if (it.status) {
                    Log.e("status true", "login--")
                    dialog.dismiss()
                    subjectlist.clear()
                    subjectlist.addAll(it.data)


//set data in subject adapter
                    val linearLayoutManager = GridLayoutManager(
                        this, 2

                    )

                    binding.rvLessons.layoutManager = linearLayoutManager
                    val subjectsAdapter = SubjectsClassidAdapter(this, subjectlist)
                    binding.rvLessons.adapter = subjectsAdapter

                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    //  showError(it.message, this)
                }
            }

        })


    }
}