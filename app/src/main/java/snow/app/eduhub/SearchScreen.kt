package snow.app.eduhub

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivitySearchScreenBinding
import snow.app.eduhub.ui.adapter.ChaptersAdapter
import snow.app.eduhub.ui.adapter.SearchChaptersAdapter
import snow.app.eduhub.ui.adapter.SearchSubjectsAdapter
import snow.app.eduhub.ui.adapter.TutorialsAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.SearchVm


class SearchScreen : BaseActivity() {
    lateinit var powerMenu: PowerMenu
    lateinit var binding: ActivitySearchScreenBinding
    lateinit var viewModel: SearchVm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_search_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_screen)
        binding.lifecycleOwner = this
        viewModel = SearchVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.ivback.setOnClickListener {
            onBackPressed()
        }

        binding.llSelect.setOnClickListener(View.OnClickListener {

            val list: MutableList<String> =
                ArrayList()
            list.add("Subjects")
            list.add("Chapters")
            show(list, binding.llSelect)
        })



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


        binding.edSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                if (isNetworkConnected()) {
                    viewModel.searchApi()
                } else {
                    showInternetToast()
                }


            }
            true
        }

        viewModel.res_subjects.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {


                    dialog.dismiss()
                    hideKeyboard(this)




                    if (it.data.subjectData.size == 0 && it.data.chapter.size == 0) {
                        binding.noRecordFound.visibility = View.VISIBLE
                        binding.rlChapters.visibility = View.GONE
                        binding.rlSubjects.visibility = View.GONE
                    } else {

                        binding.noRecordFound.visibility = View.GONE
                        viewModel.keyword_.set("")
                        if (it.data.subjectData.size == 0) {
                            val linearLayoutManager_ = LinearLayoutManager(
                                this
                            )

                            binding.rvChapters.layoutManager = linearLayoutManager_
                            val searchChaptersAdapter = SearchChaptersAdapter(this, it.data.chapter)
                            binding.rvChapters.adapter = searchChaptersAdapter
                            binding.rlChapters.visibility = View.VISIBLE
                            binding.rlSubjects.visibility = View.GONE


                        } else {


                            val linearLayoutManager_ = GridLayoutManager(
                                this,
                                2
                            )

                            binding.rvSubjects.layoutManager = linearLayoutManager_
                            val searchSubjectsAdapter =
                                SearchSubjectsAdapter(this, it.data.subjectData)
                            binding.rvSubjects.adapter = searchSubjectsAdapter

                            binding.rlSubjects.visibility = View.VISIBLE
                            binding.rlChapters.visibility = View.GONE
                        }
                    }


                } else {
                    Log.e("statusfalse", "login--")
                   // binding.noRecordFound.visibility = View.VISIBLE
                    dialog.dismiss()
                    showError(it.message, this)
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
        viewModel.selected_type.set(textSelected)



        if (textSelected.equals("Subjects")) {
            viewModel.search_type.set("1")
            viewModel.selected_hint.set("Search Subjects")
        } else {
            viewModel.search_type.set("2")
            viewModel.selected_hint.set("Search Chapters")
        }




        powerMenu.dismiss()
    }

}