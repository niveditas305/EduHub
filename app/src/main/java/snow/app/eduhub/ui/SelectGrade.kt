package snow.app.eduhub.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.unity3d.ads.UnityAds
import com.unity3d.ads.metadata.MediationMetaData
import com.unity3d.ads.metadata.PlayerMetaData
import com.unity3d.services.banners.UnityBanners
import snow.app.eduhub.MainActivity
import snow.app.eduhub.QuestionPaperPdfs
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivitySelectGradeBinding
import snow.app.eduhub.network.responses.grades.Data
import snow.app.eduhub.ui.adapter.SubjectsClassidAdapter
import snow.app.eduhub.ui.fragments.HomeFragment
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.SelectGradeVm
import snow.app.eduhub.viewmodels.SignupVm
import snow.app.eduhub.viewmodels.TopicclickVm

class SelectGrade : BaseActivity() {
    lateinit var binding: ActivitySelectGradeBinding
    lateinit var viewModel: SelectGradeVm
    lateinit var powerMenu: PowerMenu
    var subjectlist: ArrayList<snow.app.eduhub.network.responses.getSubjects.Data> = ArrayList()
    var from = ""
    var subject_id = ""
    var gradelist: ArrayList<String> = ArrayList()
    var subjectname: ArrayList<String> = ArrayList()
    lateinit var grades: List<Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_grade)
        binding.lifecycleOwner = this
        viewModel = SelectGradeVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()






/*

        if (intent.hasExtra("subject_id")) {
          //  viewModel.subjectid.set(intent.getStringExtra("subject_id").toString())
            subject_id = intent.getStringExtra("subject_id").toString()
        } else {
            from = intent.getStringExtra("from").toString()
        }*/

/*
        if (intent.hasExtra("from")) {
            from = intent.getStringExtra("from").toString()
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


        if (isNetworkConnected()) {
            viewModel.getGrades()
        } else {
            showInternetToast()
        }

        listners()
        observers()
    }

    fun listners() {
        binding.grade.setOnClickListener {
            show(gradelist, binding.grade)
        }


        binding.edClass.setOnClickListener {
            showSubject(subjectname, binding.edClass)
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }


        binding.tvSignup.setOnClickListener {

            if (binding.grade.text.toString().isEmpty()) {
                showToast("Please select grade")
            }else if (binding.edClass.text.toString().isEmpty()) {
                showToast("Please confirm your subject")
            } else {

                if (intent.hasExtra("from")) {

                    var intent = Intent(this, QuestionPaperPdfs::class.java)
                    intent.putExtra("subject_id", viewModel.subjectid.get().toString())
                    intent.putExtra("class_id", viewModel.gradeid.get().toString())
                    startActivity(intent)



                } else {

                    var intent = Intent(this, StudyGuideActivity::class.java)
                    intent.putExtra("gradeid", viewModel.gradeid.get().toString())
                    intent.putExtra("subject_id", viewModel.subjectid.get().toString())
                    startActivity(intent)



                /*    var intent = Intent(this, SubjectActivity::class.java)
                    intent.putExtra("gradeid", viewModel.gradeid.get().toString())
                    startActivity(intent)*/
                }


            }

        }
    }


    fun observers() {

        viewModel.respDataGrade.observe(this, Observer {
            Log.e("respData ", "login--")
            dialog.dismiss()
            if (it != null) {
                if (it.status) {

                    gradelist.clear()
                    grades = it.data

                    //  list.add("Grade")
                    for (x in 0 until grades.size) {
                        gradelist.add(grades.get(x).class_name.toString())
                    }

                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    //   showError(it.message, this)
                }
            }

        })
        viewModel.respData.observe(this, Observer {
            if (it != null) {
                if (it.status) {
                    Log.e("status true", "login--")
                    dialog.dismiss()
                    subjectlist.clear()
                    subjectlist.addAll(it.data)
                    subjectname.clear()


                    for (i in 0 until subjectlist.size) {
                        subjectname.add(subjectlist.get(i).subject_name)
                    }


                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
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
            .setHeaderView(R.layout.powermenu_header)
            .setTextColor(ContextCompat.getColor(this, R.color.black))
            .setTextGravity(Gravity.CENTER)
            .setTextTypeface(Typeface.create(getResources().getFont(R.font.semi), Typeface.NORMAL))
            .setSelectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setMenuColor(Color.WHITE)
            .setSelectedMenuColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setOnMenuItemClickListener(onMenuItemClickListener)
            .build()
        powerMenu.showAtCenter(view)
    }

    fun showSubject(list: List<String>, view: View?) {
        val l1: MutableList<PowerMenuItem> = ArrayList()
        for (i in list.indices) {
            l1.add(PowerMenuItem(list[i], false))
        }
        powerMenu = PowerMenu.Builder(this)
            .addItemList(l1)
            .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
            .setMenuRadius(10f)
            .setMenuShadow(10f)
            .setHeaderView(R.layout.powermenu_header_sub)
            .setTextColor(ContextCompat.getColor(this, R.color.black))
            .setTextGravity(Gravity.CENTER)
            .setTextTypeface(Typeface.create(getResources().getFont(R.font.semi), Typeface.NORMAL))
            .setSelectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setMenuColor(Color.WHITE)
            .setSelectedMenuColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setOnMenuItemClickListener(onMenuItemClickListenerSub)
            .build()
        powerMenu.showAtCenter(view)
    }

    private val onMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        val textSelected = item.title
        viewModel.grade.set(textSelected)

        for (i in 0 until grades.size) {


            if (textSelected.equals(grades.get(i).class_name)) {
                viewModel.gradeid.set(grades.get(i).id.toString())
  viewModel.getSubjectByClass(grades.get(i).id.toString())

                viewModel.subjectid.set("")
                viewModel.subject.set("")
            }


        }

        powerMenu.dismiss()
    }


    private val onMenuItemClickListenerSub = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        val textSelected = item.title
        viewModel.subject.set(textSelected)

        for (i in 0 until subjectlist.size) {


            if (textSelected.equals(subjectlist.get(i).subject_name)) {
                viewModel.subjectid.set(subjectlist.get(i).id.toString())
             }


        }

        powerMenu.dismiss()
    }


}