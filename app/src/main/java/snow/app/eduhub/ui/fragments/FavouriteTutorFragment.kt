package snow.app.eduhub.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import snow.app.eduhub.R
import snow.app.eduhub.databinding.FragmentFavouriteTutorBinding
import snow.app.eduhub.ui.adapter.FavTeachersAdapter
import snow.app.eduhub.util.BaseFragment
import snow.app.eduhub.util.LikeDislikeListener
import snow.app.eduhub.util.OnTokenExpired
import snow.app.eduhub.viewmodels.BaseViewModel
import snow.app.eduhub.viewmodels.FavTeachersVm


class FavouriteTutorFragment : BaseFragment(), LikeDislikeListener,
    OnTokenExpired.OnTokenExpiredListener {
    var viewModel: FavTeachersVm? = null
    lateinit var binding: FragmentFavouriteTutorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //   val view=inflater.inflate(R.layout.fragment_favourite_tutor, container, false)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_tutor, container, false);
        binding.lifecycleOwner = this
        viewModel = FavTeachersVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        dialog = ProgressDialog(context)
        dialog.setMessage("Please wait...")
        dialog.setCancelable(false)
        viewModell = BaseViewModel()

        viewModel?.isLoading?.observe(requireActivity(), Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel?.isError?.observe(requireActivity(), Observer {
            if (it.isError) {
                showError(it.message, requireContext());
            }

        })




//fetch teacher listing response

        viewModel?.resTeachers?.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()

                    if (it.data.size == 0) {
                        binding.noRecordFound.visibility = View.VISIBLE
                        binding.rvFavtut.visibility = View.GONE
                    } else {
                        val linearLayoutManager = LinearLayoutManager(requireContext())
                        binding.rvFavtut.layoutManager = linearLayoutManager
                        val adapter = FavTeachersAdapter(requireContext(), it.data, this)
                        binding.rvFavtut.adapter = adapter
                        binding.noRecordFound.visibility = View.GONE
                        binding.rvFavtut.visibility = View.VISIBLE
                    }


                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    //   showError(it.message, requireContext())


                }
            }

        })

        // fetch fav // unfav rsponse
        viewModel?.res_favunfav?.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    showToast(it.message)
                    refreshFragment()
                } else {
                    dialog.dismiss()
                    Log.e("statusfalse", "login--")
                    showToast(it.message)
                }
            }

        })
        return binding.root
    }

    override fun likeClick(
        id: String,
        type: String,
        imageView_fav: ImageView,
        imageView_unfav: ImageView
    ) {
        viewModel?.favUnfavTeacher(getUserToken(), type, id)
    }

    override fun onTokenExpiredListener() {
        viewModel!!.isLoading.postValue(false)

        showTokenError(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        //fetch fav teachers
        if (isNetworkConnected()) {
            viewModel?.fetchfavTeachers()
        } else {
            showInternetToast()
        }
    }
}