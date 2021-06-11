package snow.app.eduhub.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.databinding.FragmentStudyGuideBinding
import snow.app.eduhub.ui.adapter.StudyGuidePdfAdapter
import snow.app.eduhub.ui.adapter.StudyGuideVideoAdapter
import snow.app.eduhub.ui.adapter.WorksheetAdapter
import snow.app.eduhub.ui.network.responses.getstudyguild.Data
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.StudyGuideVm

class StudyGuideActivity : BaseActivity() {

    lateinit var viewModel: StudyGuideVm
    lateinit var binding: FragmentStudyGuideBinding
    var studyguideList: ArrayList<Data> = ArrayList()
    var gradeid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_study_guide)
        binding.lifecycleOwner = this
        viewModel = StudyGuideVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()



        binding.ivBack.setOnClickListener {
            onBackPressed()
        }



        if (intent.hasExtra("gradeid")) {
            gradeid = intent.getStringExtra("gradeid").toString()

            if (isNetworkConnected()) {
                viewModel?.getStudyGuide(gradeid)
            } else {
                showInternetToast()
            }
        }






        observers()
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
                    dialog.dismiss()
                    studyguideList.clear()
                    studyguideList.addAll(it.data)
                    setVideoAdapter(studyguideList)
                    setpdfAdapter(studyguideList)
                } else {
                    dialog.dismiss()
                    showError(it.message, this)
                }
            }

        })


    }

    fun setVideoAdapter(studyguideList: ArrayList<Data>) {
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideos.layoutManager = linearLayoutManager
        val tutorialsAdapter = StudyGuideVideoAdapter(this, studyguideList)
        binding.rvVideos.adapter = tutorialsAdapter
    }

    fun setpdfAdapter(studyguideList: ArrayList<Data>) {
        val linearLayoutManager_worksheet = LinearLayoutManager(this)
        binding.rvChapters.layoutManager = linearLayoutManager_worksheet
        val worksheetAdapter = StudyGuidePdfAdapter(this, studyguideList)
        binding.rvChapters.adapter = worksheetAdapter


    }


}