package snow.app.eduhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import snow.app.eduhub.ui.adapter.RatingReviewAdapter
import kotlinx.android.synthetic.main.activity_teacher_profile.*
import kotlinx.android.synthetic.main.activity_teacher_profile.iv_back
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityTeacherProfileBinding
import snow.app.eduhub.ui.adapter.TutorialsAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.TeacherProfileVm

class TeacherProfile : BaseActivity() {
    lateinit var reviewAdapter: RatingReviewAdapter
    lateinit var binding: ActivityTeacherProfileBinding
    lateinit var viewModel: TeacherProfileVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_teacher_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_profile)
        binding.lifecycleOwner = this
        viewModel = TeacherProfileVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

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

        if (intent.hasExtra("teacher_id")) {
            viewModel.teacher_id.set(intent.getStringExtra("teacher_id").toString())

            if (isNetworkConnected()) {
                viewModel.teacherProfile()
            } else {
                showInternetToast()
            }
        }



        viewModel.res_teachersprofile.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()

                    if (it.data.reviewsList.size == 0) {
                        binding.noRecordFound.visibility = View.VISIBLE
                    } else {
                        val linearLayoutManager = LinearLayoutManager(this)
                        binding.rvReviews.layoutManager = linearLayoutManager
                        reviewAdapter = RatingReviewAdapter(this, it.data.reviewsList)
                        binding.rvReviews.adapter = reviewAdapter

                        binding.noRecordFound.visibility = View.GONE
                    }


                    if (it.data.fav == 1) {
                        binding.ivFav.visibility = View.VISIBLE
                        binding.ivUnfav.visibility = View.GONE
                    } else {
                        binding.ivUnfav.visibility = View.VISIBLE
                        binding.ivFav.visibility = View.GONE
                    }

                    if (it.data.teacherRating != null) {
                        binding.ratingBar.rating = it.data.teacherRating.toFloat()
                        binding.ratingBarRating.rating = it.data.teacherRating.toFloat()
                    }


                    if (it.data.teacherDescription == null) {
                        binding.tvAbout.visibility = View.GONE
                        binding.tvAboutVa.visibility = View.GONE
                    }


                } else {
                    Log.e("statusfalse", "login--")
                    // binding.noRecordFound.visibility = View.VISIBLE

                    showError(it.message, this)
                }
            }

        })


        // fetch fav // unfav rsponse
        viewModel.res_favunfav.observe(this, Observer {
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

        binding.ivFav.setOnClickListener {


            if (isNetworkConnected()) {
                viewModel.favUnfavTeacher(
                    getUserToken(),
                    "2",
                    viewModel.teacher_id.get().toString()
                )
                binding.ivUnfav.visibility = View.VISIBLE
                binding.ivFav.visibility = View.GONE
            } else {
                showInternetToast()
            }

        }

        binding.ivUnfav.setOnClickListener {

            if (isNetworkConnected()) {
                viewModel.favUnfavTeacher(
                    getUserToken(),
                    "1",
                    viewModel.teacher_id.get().toString()
                )

                binding.ivUnfav.visibility = View.GONE
                binding.ivFav.visibility = View.VISIBLE


            } else {
                showInternetToast()
            }
        }

    }
}