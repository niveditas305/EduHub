package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_continue_detail.*
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityTestListBinding
import snow.app.eduhub.ui.adapter.ChaptersAdapter
import snow.app.eduhub.ui.adapter.OlderTestLIstAdapter
import snow.app.eduhub.ui.adapter.TestLIstAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.TestListVM

class TestListActivity : BaseActivity() {

    lateinit var binding: ActivityTestListBinding
    lateinit var viewModel: TestListVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_test_list)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_list)
        binding.lifecycleOwner = this
        viewModel = TestListVM(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.ivback.setOnClickListener {
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


        if (intent.hasExtra("subjectid")) {
            viewModel.subject_id.set(intent.getStringExtra("subjectid"))
            viewModel.subjectname.set(intent.getStringExtra("subjectname"))
        }


        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {


                    if (it.data.currentWeekTest.size == 0 && it.data.oldWeekTest.size == 0) {
                        binding.noRecordFound.visibility = View.VISIBLE



                        binding.rvOlder.visibility = View.GONE
                        binding.rvLatest.visibility = View.GONE
                    } else {

                        binding.noRecordFound.visibility = View.GONE
                        binding.rvOlder.visibility = View.VISIBLE
                        binding.rvLatest.visibility = View.VISIBLE



                        if (it.data.currentWeekTest.size > 0) {
                            val linearLayoutManagertut = LinearLayoutManager(this)
                            binding.rvThisweektest.layoutManager = linearLayoutManagertut
                            val testLIstAdapter = TestLIstAdapter(this, it.data.currentWeekTest)
                            binding.rvThisweektest.adapter = testLIstAdapter

                        } else {
                            binding.rvLatest.visibility = View.GONE
                        }

                        if (it.data.oldWeekTest.size > 0) {
                            val linearLayoutManagertut = LinearLayoutManager(this)
                            binding.rvOldertests.layoutManager = linearLayoutManagertut
                            val testLIstAdapter = OlderTestLIstAdapter(this, it.data.oldWeekTest)
                            binding.rvOldertests.adapter = testLIstAdapter

                        } else {
                            binding.rvOldertests.visibility = View.GONE
                        }

                    }
                } else {
                    Log.e("statusfalse", "login--")
                    //  binding.tvNoRecordFound.visibility = View.VISIBLE
                    //  binding.llTwo.visibility = View.GONE
                    //  showError(it.message, this)
                }
            }

        })



    }


    override fun onResume() {
        super.onResume()
        //fetch test list
        if (isNetworkConnected()) {
            viewModel.testList()
        } else {
            showInternetToast()
        }
    }
}