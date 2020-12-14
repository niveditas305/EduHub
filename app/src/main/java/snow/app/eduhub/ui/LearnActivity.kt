package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityLearnBinding
import snow.app.eduhub.ui.adapter.SubjectListAdapter
import snow.app.eduhub.ui.adapter.SubjectListByIdAdapter
import snow.app.eduhub.ui.network.responses.getsubjectlistbyid.Data
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.SubjectByIdVm

class LearnActivity : BaseActivity() {

    lateinit var binding: ActivityLearnBinding
    lateinit var viewModel: SubjectByIdVm

    val arrayList: ArrayList<Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_learn)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learn)
        binding.lifecycleOwner = this
        viewModel = SubjectByIdVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }



        if (intent.hasExtra("teacher_id")) {
            viewModel.teacher_id.set(intent.getStringExtra("teacher_id"))
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
        //fetch home data response
        viewModel.respDataSubjectsbyId.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()

                    if (it.data.size > 0) {

                        arrayList.clear()
                        for (i in 0 until it.data.size) {
                            if (getSession()?.getAppData()?.data?.schoolClassId == it.data.get(i).schoolClassId) {
                                arrayList.add(it.data.get(i))

                                Log.e(
                                    "statusfalse",
                                    "getSession()?.getAppData()?.data?.schoolClassId--" + getSession()?.getAppData()?.data?.schoolClassId +
                                            "---" +it.data.get(i).schoolClassId.toString()
                                )

                            }
                        }



                        Log.e("arraykist", "login--" + arrayList.size)
                        if (arrayList.size == 0) {
                            binding.rvLessons.visibility = View.GONE
                            binding.noRecordFound.visibility = View.VISIBLE
                        } else {
                            binding.rvLessons.visibility = View.VISIBLE
                            binding.noRecordFound.visibility = View.GONE
                        }
                        val linearLayoutManager_ = GridLayoutManager(this, 2)
                        binding.rvLessons.layoutManager = linearLayoutManager_
                        val searchSubjectsAdapter = SubjectListByIdAdapter(this, arrayList)
                        binding.rvLessons.adapter = searchSubjectsAdapter


                    } else {
                        binding.rvLessons.visibility = View.GONE
                        binding.noRecordFound.visibility = View.VISIBLE
                    }


                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    showError(it.message, this)
                }
            }

        })



        if (isNetworkConnected()) {
            viewModel.getSubjectsByTearcherID()
        } else {
            showInternetToast()
        }

    }
}