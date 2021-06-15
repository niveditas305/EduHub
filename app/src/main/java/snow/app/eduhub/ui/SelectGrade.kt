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
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.unity3d.ads.UnityAds
import com.unity3d.ads.metadata.MediationMetaData
import com.unity3d.ads.metadata.PlayerMetaData
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivitySelectGradeBinding
import snow.app.eduhub.databinding.ActivityTopicClicksBinding
import snow.app.eduhub.databinding.CompleteChapterDialogBinding
import snow.app.eduhub.network.responses.grades.Data
import snow.app.eduhub.ui.fragments.HomeFragment
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.SelectGradeVm
import snow.app.eduhub.viewmodels.SignupVm
import snow.app.eduhub.viewmodels.TopicclickVm

class SelectGrade : BaseActivity() {
    lateinit var binding: ActivitySelectGradeBinding
    lateinit var viewModel: SelectGradeVm
    lateinit var powerMenu: PowerMenu
    var gradelist: ArrayList<String> = ArrayList()
    lateinit var grades: List<Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_grade)
        binding.lifecycleOwner = this
        viewModel = SelectGradeVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()


        viewModel?.isLoading?.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel?.isError?.observe(this, Observer {
            if (it.isError) {
                showError(it.message, this);
            }

        })


        if (isNetworkConnected()) {
            viewModel.getGrades()
        }else{
            showInternetToast()
        }

        listners()
        observers()
    }
    fun listners(){
        binding.grade.setOnClickListener {
            show(gradelist, binding.grade)
        }


        binding.ivBack.setOnClickListener {
            onBackPressed()
        }


        binding.tvSignup.setOnClickListener {

            if (binding.grade.text.toString().isEmpty()){
                showToast("Please select class")
            }else{
                startActivity(Intent(this,StudyGuideActivity::class.java).
                putExtra("gradeid",viewModel.gradeid.get().toString()))
            }

        }
    }



    fun observers(){

        viewModel?.respDataGrade?.observe(this, Observer {
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

    private val onMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        val textSelected = item.title
        viewModel?.grade?.set(textSelected)

        for (i in 0 until grades.size) {


            if (textSelected.equals(grades.get(i).class_name)) {
                viewModel?.gradeid?.set(grades.get(i).id.toString())
            }


        }

        powerMenu.dismiss()
    }

}