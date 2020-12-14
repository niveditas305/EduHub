package snow.app.eduhub.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.ChaptersAdapter
import snow.app.eduhub.ui.adapter.TutorialsAdapter
import kotlinx.android.synthetic.main.activity_continue_detail.*
import snow.app.eduhub.SearchScreen
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityTeacherListingScreenBinding
import snow.app.eduhub.network.responses.grades.Data
import snow.app.eduhub.ui.network.responses.getChapters.Chapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.AllTeachersVM
import snow.app.eduhub.viewmodels.ChaptersVM

class ContinueDetails : BaseActivity() {
    lateinit var chaptersAdapter: ChaptersAdapter
    lateinit var tutorialsAdapter: TutorialsAdapter
    lateinit var binding: ActivityContinueDetailBinding
    lateinit var viewModel: ChaptersVM

    var chapterswithtopicslist: ArrayList<Chapter> =
        ArrayList()
    var chaptersnamelist: ArrayList<String> = ArrayList()
    lateinit var powerMenu: PowerMenu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_continue_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_continue_detail)
        binding.lifecycleOwner = this
        viewModel = ChaptersVM(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.ivback.setOnClickListener {
            onBackPressed()
        }


        if (intent.hasExtra("teacherId")) {
            viewModel.teacher_id.set(intent.getStringExtra("teacherId").toString())
            viewModel.subject_id.set(intent.getStringExtra("subjectId").toString())
            viewModel.subjectname.set(intent.getStringExtra("subjectname").toString())
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

        binding.fSearch.setOnClickListener {
            startActivity(Intent(this, SearchScreen::class.java))
        }

        //fetch chapter
        if (isNetworkConnected()) {
            viewModel.getChapters()
        } else {
            showInternetToast()
        }




        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {

                    chapterswithtopicslist.clear()
                    chaptersnamelist.clear()


                    if (it.data.chapter.size > 0) {
                        val linearLayoutManagertut = LinearLayoutManager(this)
                        rv_chapters.layoutManager = linearLayoutManagertut
                        chaptersAdapter = ChaptersAdapter(this, it.data.chapter, this)
                        rv_chapters.adapter = chaptersAdapter

                        chapterswithtopicslist.addAll(it.data.chapter)
                        for (i in 0 until chapterswithtopicslist.size) {
                            chaptersnamelist.add(chapterswithtopicslist.get(i).chapterName)


                        }


                        if (chapterswithtopicslist.size > 0) {
                            viewModel.chapter_id.set(chapterswithtopicslist.get(0).id.toString())
                            viewModel.selected_chapter.set(chapterswithtopicslist.get(0).chapterName.toString())
                            if (isNetworkConnected()) {
                                viewModel.getChapterVideos()
                            } else {
                                showInternetToast()
                            }
                        }
                        binding.tvNoRecordFound.visibility = View.GONE


                        if (it.data.continueTopic != null) {
                            viewModel.continue_topic.set(it.data.continueTopic.topicName)
                            binding.llTwo.visibility = View.VISIBLE
                        } else {
                            viewModel.continue_topic.set("")
                            binding.llTwo.visibility = View.GONE
                        }


                    } else {
                        binding.llTwo.visibility = View.GONE
                    }
                } else {
                    Log.e("statusfalse", "login--")
                    binding.tvNoRecordFound.visibility = View.VISIBLE
                    binding.llTwo.visibility = View.GONE
                    //  showError(it.message, this)
                }
            }

        })



        binding.tvSelected.setOnClickListener {


            if (chapterswithtopicslist.size > 0) {
                show(chaptersnamelist, binding.tvSelected)
            }

        }

        viewModel.respVideoData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    val linearLayoutManager = GridLayoutManager(
                        this,
                        2
                    )
                    binding.rvVideos.layoutManager = linearLayoutManager
                    tutorialsAdapter = TutorialsAdapter(this, it.data, this)
                    binding.rvVideos.adapter = tutorialsAdapter
                    binding.noRecordFound.visibility = View.GONE

                    binding.tvSelected.visibility = View.VISIBLE


                    if (it.data.size > 4) {
                        binding.tvLoadmore.visibility = View.VISIBLE
                    } else {
                        binding.tvLoadmore.visibility = View.GONE
                    }
                } else {
                    Log.e("statusfalse", "login--")
                    binding.noRecordFound.visibility = View.VISIBLE
                    binding.tvLoadmore.visibility = View.GONE
                    binding.tvSelected.visibility = View.GONE
                    //  showError(it.message, this)
                }
            }

        })


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
        viewModel.selected_chapter.set(textSelected)

        for (i in 0 until chapterswithtopicslist.size) {


            if (textSelected.equals(chapterswithtopicslist.get(i).chapterName)) {
                viewModel.chapter_id.set(chapterswithtopicslist.get(i).id.toString())
            }


        }

        if (chapterswithtopicslist.size > 0) {
            viewModel.getChapterVideos()
        }


        powerMenu.dismiss()
    }

}