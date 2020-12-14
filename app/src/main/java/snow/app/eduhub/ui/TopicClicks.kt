package snow.app.eduhub.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.TutorialsAdapter
import snow.app.eduhub.ui.adapter.WorksheetPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import kotlinx.android.synthetic.main.activity_topic_clicks.*
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityTopicClickScreenBinding
import snow.app.eduhub.databinding.ActivityTopicClicksBinding
import snow.app.eduhub.databinding.CompleteChapterDialogBinding
import snow.app.eduhub.network.responses.grades.Data
import snow.app.eduhub.ui.adapter.TopicVideosAdapter
import snow.app.eduhub.ui.adapter.WorksheetAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.TopicclickVm

class TopicClicks : BaseActivity() {
    lateinit var binding: ActivityTopicClicksBinding
    lateinit var completeChapterDialogBinding: CompleteChapterDialogBinding
    lateinit var viewModel: TopicclickVm
    var mAlertDialog: AlertDialog? = null
    lateinit var topiclist: List<snow.app.eduhub.ui.network.responses.topiclistres.Data>
    var topicnamelist: ArrayList<String> = ArrayList()
    lateinit var powerMenu: PowerMenu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_topic_clicks)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_clicks)
        binding.lifecycleOwner = this
        viewModel = TopicclickVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        if (intent.hasExtra("teacherId")) {
            viewModel.chapter_id.set(intent.getStringExtra("chapterId"))
            viewModel.teacher_id.set(intent.getStringExtra("teacherId"))
            viewModel.subject_id.set(intent.getStringExtra("subjectId"))
            viewModel.topic_id.set(intent.getStringExtra("topic_id"))
        }


        binding.ivComplete.setOnClickListener {
            showCompleteDialog()
        }

        /*      tb_w.addTab(tb_w.newTab().setText(getString(R.string.all)))
              tb_w.addTab(tb_w.newTab().setText(getString(R.string.ongoing)))
              tb_w.addTab(tb_w.newTab().setText(getString(R.string.completed)))
              tb_w.tabGravity = TabLayout.GRAVITY_FILL*/

        /*      val worksheetPagerAdapter = WorksheetPagerAdapter(supportFragmentManager)
              binding.vpWorksheet.adapter = worksheetPagerAdapter

              binding.vpWorksheet.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tbW))
              binding.tbW.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                  override fun onTabSelected(tab: TabLayout.Tab) {
                      binding.vpWorksheet.currentItem = tab.position
                  }
                  override fun onTabUnselected(tab: TabLayout.Tab) {
                  }
                  override fun onTabReselected(tab: TabLayout.Tab) {
                  }
              })*/

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






        binding.edTopicname.setOnClickListener {
            show(topicnamelist, binding.edTopicname)
        }

        viewModel.respData_topiclist.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    if (it.data.size > 0) {
                        topiclist = it.data



                        viewModel.topic_id.set(topiclist.get(0).id.toString())
                        viewModel.selected_topic.set(topiclist.get(0).topicName.toString())
                        if (isNetworkConnected()) {
                            viewModel.getTopicDetails()
                        }else{
                            showInternetToast()
                        }




                        for (x in 0 until it.data.size) {
                            topicnamelist.add(it.data.get(x).topicName.toString())
                        }
                    }


                } else {

                    //  showError(it.message, this)
                }
            }

        })
        viewModel.res_rating.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    mAlertDialog?.dismiss()
                    showToast(it.message)
                } else {
                    dialog.dismiss()
                    showToast(it.message)
                    //  showError(it.message, this)
                }
            }

        })


        viewModel.respVideoData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()

                    if (it.data.videos.size == 0 && it.data.worksheets.size == 0) {
                        binding.llData.visibility = View.GONE
                        binding.noData.visibility = View.VISIBLE
                    } else {
                        binding.llData.visibility = View.VISIBLE
                        binding.noData.visibility = View.GONE



                        if (it.data.videos.size > 0) {

                            val linearLayoutManager =
                                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                            binding.rvvideos.layoutManager = linearLayoutManager
                            val tutorialsAdapter = TopicVideosAdapter(this, it.data.videos, this)
                            binding.rvvideos.adapter = tutorialsAdapter


                            binding.noRecordFound.visibility = View.GONE
                            binding.rvvideos.visibility = View.VISIBLE


                        } else {
                            binding.noRecordFound.visibility = View.VISIBLE
                            binding.rvvideos.visibility = View.GONE
                        }





                        if (it.data.worksheets.size > 0) {
                            //set worksheet adapter
                            val linearLayoutManager_worksheet = LinearLayoutManager(this)
                            binding.rvPdfs.layoutManager = linearLayoutManager_worksheet
                            val worksheetAdapter = WorksheetAdapter(this, it.data.worksheets, this)
                            binding.rvPdfs.adapter = worksheetAdapter


                            binding.noPdfs.visibility = View.GONE
                            binding.rvPdfs.visibility = View.VISIBLE

                        } else {
                            binding.noPdfs.visibility = View.VISIBLE
                            binding.rvPdfs.visibility = View.GONE
                        }

                    }
                } else {
                    binding.llData.visibility = View.GONE
                    binding.noData.visibility = View.VISIBLE
                    //  showError(it.message, this)
                }
            }

        })

        if (isNetworkConnected()) {
            viewModel.getTopiclist()
        } else {
            showInternetToast()
        }

    }


    fun show(list: List<String>, view: View?) {
        val l1: MutableList<PowerMenuItem> = ArrayList()
        for (i in list.indices) {
            l1.add(PowerMenuItem(list[i], false))
        }
        powerMenu = PowerMenu.Builder(this)
            .addItemList(l1)
            .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
            .setMenuRadius(10f)
            .setMenuShadow(10f)
            .setTextColor(ContextCompat.getColor(this, R.color.black))
            .setTextGravity(Gravity.CENTER)
            .setSelectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setMenuColor(Color.WHITE)
            .setSelectedMenuColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setOnMenuItemClickListener(onMenuItemClickListener)
            .build()
        powerMenu.showAtCenter(view)
    }

    private val onMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        val textSelected = item.title
        viewModel.selected_topic.set(textSelected)

        for (i in 0 until topiclist.size) {


            if (textSelected.equals(topiclist.get(i).topicName)) {
                viewModel.topic_id.set(topiclist.get(i).id.toString())
            }


        }
        if (topiclist.size > 0) {

            viewModel.getTopicDetails()
        }

        powerMenu.dismiss()
    }

    fun showCompleteDialog() {
        completeChapterDialogBinding = DataBindingUtil.bind(
            layoutInflater.inflate(
                R.layout.complete_chapter_dialog,
                null
            )
        )!!
        //    val mDialogView = LayoutInflater.from(this).inflate(R.layout.broadcast_accept_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(completeChapterDialogBinding.root)


        mAlertDialog = mBuilder.show()
        mAlertDialog!!.setCancelable(false)
        completeChapterDialogBinding.lifecycleOwner = this
        //  bindingrow.viewModel = broadcastVm

        completeChapterDialogBinding.viewModel = viewModel
        completeChapterDialogBinding.executePendingBindings()

        //show dialog

        mAlertDialog?.findViewById<ImageView>(R.id.iv_cancel)?.setOnClickListener {
            mAlertDialog?.dismiss()
        }

        mAlertDialog?.findViewById<TextView>(R.id.tv_give)?.setOnClickListener {
            completeChapterDialogBinding.llRating.visibility = View.VISIBLE

        }


        completeChapterDialogBinding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            completeChapterDialogBinding.tvRating.text = rating.toString() + " Out of 5.0 Stars"

        }
        viewModel.rating_value.set(completeChapterDialogBinding.ratingBar.rating.toString())
        completeChapterDialogBinding.btnRate.setOnClickListener {
            if (isNetworkConnected()) {

                viewModel.giveRating()
            } else {
             showInternetToast()
            }

        }
    }


}