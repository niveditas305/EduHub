package snow.app.eduhub.ui

import android.os.Bundle
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityPrivacyScreenBinding
import snow.app.eduhub.util.AppUtils
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.PrivacyPolicyVM

class PrivacyScreen : BaseActivity() {

    lateinit var binding: ActivityPrivacyScreenBinding
    lateinit var viewModel: PrivacyPolicyVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_privacy_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_screen)
        binding.lifecycleOwner = this
        viewModel = PrivacyPolicyVM()
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
                     binding.tvP.setText(HtmlCompat.fromHtml(it.data.get(0).page_content, 0))

                } else {

                }
            }

        })


        if (isNetworkConnected()) {
            viewModel.getPrivacyPolicy()
        } else {
            showInternetToast()
        }
    }
}