package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_change_password.*
import snow.app.eduhub.databinding.ActivityAboutScreenBinding
import snow.app.eduhub.databinding.ActivityChangePasswordBinding
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.AboutVM
import snow.app.eduhub.viewmodels.ChangePasswordVM


class AboutScreen : BaseActivity() {


    lateinit var binding: ActivityAboutScreenBinding
    lateinit var viewModel: AboutVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_about_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_screen)
        binding.lifecycleOwner = this
        viewModel = AboutVM()
        binding.viewModel = viewModel
        binding.executePendingBindings()
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

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                     binding.tvAbout.setText(HtmlCompat.fromHtml(it.data.get(0).page_content, 0))

                } else {

                   // showError(it.message, this)
                }
            }

        })


        if (isNetworkConnected()) {
            viewModel.getAboutData()
        } else {
            showInternetToast()
        }
    }
}