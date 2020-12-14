package snow.app.eduhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import snow.app.eduhub.ui.adapter.WorksheetAdapter
import kotlinx.android.synthetic.main.activity_question_paper_pdfs.*
import kotlinx.android.synthetic.main.activity_teacher_profile.iv_back
import snow.app.eduhub.databinding.ActivityQuestionBankCategoryScreenBinding
import snow.app.eduhub.databinding.ActivityQuestionPaperPdfsBinding
import snow.app.eduhub.ui.adapter.PastQueCatAdapter
import snow.app.eduhub.ui.adapter.PastQuestionpprAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.PastQuestionpprpdfsVm
import snow.app.eduhub.viewmodels.QuestionbankcategoryVm

class QuestionPaperPdfs : BaseActivity() {

    lateinit var viewModel: PastQuestionpprpdfsVm
    lateinit var binding: ActivityQuestionPaperPdfsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_question_paper_pdfs)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_question_paper_pdfs)
        binding.lifecycleOwner = this
        viewModel = PastQuestionpprpdfsVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        if (intent.hasExtra("past_question_category_id")) {

            viewModel.subject_id.set(intent.getStringExtra("subject_id"))
            viewModel.past_question_category_id.set(intent.getStringExtra("past_question_category_id"))
            viewModel.past_question_category_name.set(intent.getStringExtra("past_question_category_name"))

            binding.title.setText(intent.getStringExtra("past_question_category_name"))
        }


        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()


                    if (it.data.size > 0) {
                        val linearLayoutManagertut = LinearLayoutManager(this)
                        binding.rvPprPast.layoutManager = linearLayoutManagertut
                        var pastQueCatAdapter = PastQuestionpprAdapter(this, it.data, this)
                        binding.rvPprPast.adapter = pastQueCatAdapter


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
            viewModel.fetchPastQuestionpprs()
        } else {
            showInternetToast()
        }
    }
}