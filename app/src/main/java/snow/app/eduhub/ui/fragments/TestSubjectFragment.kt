package snow.app.eduhub.ui.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import snow.app.eduhub.R
import snow.app.eduhub.databinding.FragmentLearnBinding
import snow.app.eduhub.databinding.FragmentProfileBinding
import snow.app.eduhub.ui.ContinueDetails
import snow.app.eduhub.ui.adapter.*
import snow.app.eduhub.util.BaseFragment
import snow.app.eduhub.viewmodels.LessonVM
import snow.app.eduhub.viewmodels.ProfileViewModel


class TestSubjectFragment : BaseFragment() {

    lateinit var viewModel: LessonVM
    lateinit var binding: FragmentLearnBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // val view=inflater.inflate(R.layout.fragment_learn, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learn, container, false);
        binding.lifecycleOwner = this
        viewModel = LessonVM(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        dialog = ProgressDialog(context)
        dialog.setMessage("Please wait...")
        dialog.setCancelable(false)
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
        //fetch home data response
        viewModel.respDataSubjects.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()

                    if (it.data.size > 0) {
                        val linearLayoutManager_ = GridLayoutManager(requireContext(), 2)
                        binding.rvLessons.layoutManager = linearLayoutManager_
                        val searchSubjectsAdapter = TestSubjectListAdapter(requireContext(), it.data)
                        binding.rvLessons.adapter = searchSubjectsAdapter

                        binding.rvLessons.visibility = View.VISIBLE
                        binding.noRecordFound.visibility = View.GONE
                    } else {
                        binding.rvLessons.visibility = View.GONE
                        binding.noRecordFound.visibility = View.VISIBLE
                    }


                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    showError(it.message, requireContext())
                }
            }

        })



        if (isNetworkConnected()) {
            viewModel.getSubjects()
        } else {
            showInternetToast()
        }

/*        binding.tvMainTwo.setOnClickListener {
            startActivity(Intent(requireContext(),ContinueDetails::class.java))
        }
        binding.tvOne.setOnClickListener {
            startActivity(Intent(requireContext(),ContinueDetails::class.java))
        }*/
        return binding.root
    }


}