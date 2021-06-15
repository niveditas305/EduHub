package snow.app.eduhub.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import snow.app.eduhub.util.BaseFragment
import snow.app.eduhub.util.PdfClickInterface
import snow.app.eduhub.viewmodels.StudyGuideVm


class StudyGuideFragment : BaseFragment(),PdfClickInterface {
    lateinit var rv_all: RecyclerView
    lateinit var viewModel: StudyGuideVm
    lateinit var binding: FragmentStudyGuideBinding
    var studyguideList: ArrayList<Data> = ArrayList()

    lateinit var worksheetAdapter: WorksheetAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_study_guide, container, false);
        binding.lifecycleOwner = this
        viewModel = StudyGuideVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        dialog = ProgressDialog(context)
        dialog.setMessage("Please wait...")
        dialog.setCancelable(false)





        observers()
        return binding.root
    }

    fun observers() {
        viewModel.isLoading.observe(requireActivity(), Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel.isError.observe(requireActivity(), Observer {
            if (it.isError) {
                showError(it.message, requireContext());
            }

        })


        viewModel.respData.observe(requireActivity(), Observer {
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    studyguideList.clear()
                    studyguideList.addAll(it.data)


                    setVideoAdapter(studyguideList)
                    setpdfAdapter(studyguideList)
                } else {
                    dialog.dismiss()
                    showError(it.message, requireActivity())
                }
            }

        })


    }

    fun setVideoAdapter(studyguideList: ArrayList<Data>) {
        val linearLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideos.layoutManager = linearLayoutManager
        val tutorialsAdapter = StudyGuideVideoAdapter(requireContext(), studyguideList,this)
        binding.rvVideos.adapter = tutorialsAdapter

    }


    fun setpdfAdapter(studyguideList: ArrayList<Data>) {
        val linearLayoutManager_worksheet = LinearLayoutManager(requireContext())
        binding.rvChapters.layoutManager = linearLayoutManager_worksheet
        val worksheetAdapter = StudyGuidePdfAdapter(requireContext(), studyguideList,this)
        binding.rvChapters.adapter = worksheetAdapter


    }

    override fun onSubmitClick(url: String, s: String) {

    }

}