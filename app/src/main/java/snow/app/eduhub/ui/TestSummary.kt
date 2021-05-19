package snow.app.eduhub.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_continue_detail.*
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_test_summary.*
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityTestSummaryBinding
import snow.app.eduhub.ui.adapter.ChaptersAdapter
import snow.app.eduhub.ui.adapter.TestSummrayAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.TestSummaryVm

class TestSummary : BaseActivity() {
    lateinit var binding: ActivityTestSummaryBinding
    lateinit var viewModel: TestSummaryVm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    setContentView(R.layout.activity_test_summary)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_summary)
        binding.lifecycleOwner = this
        viewModel = TestSummaryVm(getUserToken())
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


        if (intent.hasExtra("testid")) {
            viewModel.test_id.set(intent.getStringExtra("testid"))
            viewModel.test_unique_id.set(intent.getStringExtra("test_unique_id"))
        }

        //fetch chapter
        if (isNetworkConnected()) {
            viewModel.fetchSummary()
        }else{
            showInternetToast()
        }



        binding.tvViewSolPdf.setOnClickListener {
            if (isNetworkConnected()) {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.sol_pdf.get().toString()))
                startActivity(browserIntent)
            } else {
                Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT)
                    .show()
            }

        }



        binding.tvEndNow.setOnClickListener {
            finish()
        }




        viewModel.resSummary.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    val linearLayoutManagertut = GridLayoutManager(this, 5)
                    binding.rvScores.layoutManager = linearLayoutManagertut
                    var testSummrayAdapter = TestSummrayAdapter(this, it.data)
                    binding.rvScores.adapter = testSummrayAdapter
                    viewModel.sol_pdf.set(it.answerPdf)
                    binding.tvPercentage.setText("" + it.accuracyPercentage + "%")
                } else {

                }
            }

        })


    }
}