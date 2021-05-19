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
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.FragmentSettingBinding
import snow.app.eduhub.ui.*
import snow.app.eduhub.util.BaseFragment
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.ProfileViewModel
import snow.app.eduhub.viewmodels.SettingVM


class SettingFragment : BaseFragment() {

    lateinit var binding: FragmentSettingBinding
    lateinit var viewModel: SettingVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //     val view = inflater.inflate(R.layout.fragment_setting, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        binding.lifecycleOwner = this
        viewModel = SettingVM(getUserToken())
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
        viewModel.respData.observe(requireActivity(), Observer {
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()

                    showToast(it.message)
                } else {

                    showError(it.message, requireContext())
                }
            }

        })
        binding.sNoti.setOnClickListener {

            if (isNetworkConnected()) {
                if (binding.sNoti.isChecked) {
                    viewModel.changeNotificationStatus("1")
                } else {
                    viewModel.changeNotificationStatus("0")
                }
            } else {
                showInternetToast()
            }


        }

        viewModel.respDataGetStatus.observe(requireActivity(), Observer {
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    if (it.data == 0) {
                        binding.sNoti.isChecked = false
                    } else {
                        binding.sNoti.isChecked = true
                    }
                } else {
                    showError(it.message, requireContext())
                }
            }

        })

        binding.changePwd.setOnClickListener {
            startActivity(Intent(requireContext(), ChangePassword::class.java))
        }
        binding.contactus.setOnClickListener {
            startActivity(Intent(requireContext(), ContactUs::class.java))
        }
        binding.tvPrivacy.setOnClickListener {
            startActivity(Intent(requireContext(), PrivacyScreen::class.java))
        }
        binding.tvAbout.setOnClickListener {
            startActivity(Intent(requireContext(), AboutScreen::class.java))
        }
        binding.tvTerms.setOnClickListener {
            startActivity(Intent(requireContext(), TermsConditiions::class.java))
        }
        viewModel.respData.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    //showSuccess(it.message, requireActivity())
                } else {
                    Log.e("statusfalse", "login--")
                    // showError(it.message, requireActivity())
                }
            }

        })



        if (isNetworkConnected()) {
            viewModel.getNotificationStatus()
        } else {
            showInternetToast()
        }

        return binding.root
    }


}