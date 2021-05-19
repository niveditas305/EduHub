package snow.app.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_continue_detail.*
import kotlinx.android.synthetic.main.activity_question_bank_category_screen.*
import snow.app.eduhub.QuestionPaperPdfs
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityQuestionBankCategoryScreenBinding
import snow.app.eduhub.ui.adapter.ChaptersAdapter
import snow.app.eduhub.ui.adapter.PastQueCatAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.QuestionbankcategoryVm


class QuestionBankCategoryScreen : BaseActivity() {

    lateinit var viewModel: QuestionbankcategoryVm
    lateinit var binding: ActivityQuestionBankCategoryScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_question_bank_category_screen)
        binding.lifecycleOwner = this
        viewModel = QuestionbankcategoryVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        //   setContentView(R.layout.activity_question_bank_category_screen)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        /* tv_end.setOnClickListener {
             startActivity(Intent(this, QuestionPaperPdfs::class.java))
         }
         tv_mid.setOnClickListener {
             startActivity(Intent(this, QuestionPaperPdfs::class.java))
         }*/

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
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()


                    if (it.data.size > 0) {
                        val linearLayoutManagertut = LinearLayoutManager(this)
                        binding.rvPprCat.layoutManager = linearLayoutManagertut
                        var pastQueCatAdapter = PastQueCatAdapter(this, it.data)
                        binding.rvPprCat.adapter = pastQueCatAdapter


                        binding.noRecordFound.visibility = View.GONE


                    } else {
                        binding.noRecordFound.visibility = View.VISIBLE

                    }
                } else {
                    Log.e("statusfalse", "login--")
                    binding.noRecordFound.visibility = View.VISIBLE
                    //  showError(it.message, this)
                }
            }

        })



        if (isNetworkConnected()) {
            viewModel.fetchQuestionPprCat()
        } else {
            showInternetToast()
        }

    }
}