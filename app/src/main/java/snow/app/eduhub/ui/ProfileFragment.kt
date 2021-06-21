package snow.app.eduhub.ui

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import snow.app.eduhub.MainActivity
import snow.app.eduhub.R
import snow.app.eduhub.databinding.FragmentProfileBinding
import snow.app.eduhub.network.responses.grades.Data
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.BaseFragment
import snow.app.eduhub.viewmodels.ProfileViewModel
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class ProfileFragment : BaseFragment() {
    lateinit var viewModel: ProfileViewModel
    lateinit var binding: FragmentProfileBinding
    lateinit var powerMenu: PowerMenu
    lateinit var grades: List<Data>
    var locale: String = ""
    var gradelist: ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //  val view=inflater.inflate(R.layout.fragment_profile, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.lifecycleOwner = this
        viewModel = ProfileViewModel(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        dialog = ProgressDialog(context)
        dialog.setMessage("Please wait...")
        dialog.setCancelable(false)



        locale = getResources().getConfiguration().locale.getCountry()
        //  Log.e("locale","--"+locale)
        binding.ccp.setDefaultCountryUsingNameCode(locale)
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

        binding.ivProfile.setOnClickListener {
            context?.let { it1 ->
                CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(it1, this)
            }
        }

        //show class list
        binding.edClass.setOnClickListener {
            show(gradelist, binding.edClass)
        }


        binding.tvUpdate.setOnClickListener {
            if (isNetworkConnected()) {
                viewModel.update(binding.ccp.selectedCountryCode)
            } else {
                showInternetToast()
            }
        }


        //fetch profile response
        viewModel.respData.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()


                    if (it.data.country_code != null) {
                        binding.ccp.setCountryForPhoneCode(it.data.country_code.toInt())
                    } else {
                        binding.ccp.setDefaultCountryUsingNameCode(locale)
                    }

                    // AppUtils.roundImageWithGlide(binding.ivProfile, it.data.studentImage)
                    // showSuccess(it.message, requireActivity())
                } else {
                    dialog.dismiss()
                    Log.e("statusfalse", "login--")
                    showError(it.message, requireActivity())
                }
            }

        })


        //update profile response
        viewModel.respDataupdate.observe(requireActivity(), Observer {
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    showToast(it.message)
                    val data: SignupRes = getSession()?.getAppData()!!
                    data.data.firstName = it.data.firstName
                    data.data.lastName = it.data.lastName
                    data.data.email = it.data.email
                    data.data.studentMoblie = it.data.studentMoblie
                    data.data.country_code = it.data.country_code
                    data.data.schoolClassName = it.data.schoolClassName
                    data.data.studentImage = it.data.studentImage
                    data.data.studentSchool = it.data.studentSchool
                    if (it.data.studentAddress == null || it.data.studentAddress.equals("null")) {

                        data.data.studentAddress = ""
                    } else {
                        data.data.studentAddress = it.data.studentAddress
                    }


                    getSession()?.saveSession(data)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                } else {
                    dialog.dismiss()
                    showError("Something went wrong", requireContext())
                }
            }

        })
//fetch
        viewModel.respDataGrade.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    grades = it.data

                    //  list.add("Grade")
                    for (x in 0 until grades.size) {
                        gradelist.add(grades.get(x).class_name.toString())
                    }

                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    //   showError(it.message, this)
                }
            }

        })


        if (isNetworkConnected()) {
            viewModel.getUserDetail()
        } else {
            showInternetToast()
        }

        //get grades
        if (isNetworkConnected()) {
            viewModel.getGrades()
        } else {
            showInternetToast()
        }


        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    fun show(list: List<String>, view: View?) {
        val l1: MutableList<PowerMenuItem> = ArrayList()
        for (i in list.indices) {
            l1.add(PowerMenuItem(list[i], false))
        }
        powerMenu = PowerMenu.Builder(requireContext())
            .addItemList(l1)
            .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
            .setMenuRadius(10f)
            .setHeaderView(R.layout.powermenu_header_grade)
            .setMenuShadow(10f)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setTextGravity(Gravity.CENTER)
            .setSelectedTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            .setMenuColor(Color.WHITE)
            .setTextTypeface(
                Typeface.create(
                    getResources().getFont(R.font.semi),
                    Typeface.NORMAL
                )
            )
            .setSelectedMenuColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            .setOnMenuItemClickListener(onMenuItemClickListener)
            .build()
        powerMenu.showAtCenter(view)
    }

    private val onMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        val textSelected = item.title
        viewModel.classname.set(textSelected)

        for (i in 0 until grades.size) {
            if (textSelected.equals(grades.get(i).class_name)) {
                viewModel.class_id.set(grades.get(i).id.toString())
            }
        }

        powerMenu.dismiss()
    }


}