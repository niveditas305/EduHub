package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_terms_conditiions.*
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityTermsConditiionsBinding
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.TermsAndConditionsVM

class TermsConditiions : BaseActivity() {
    lateinit var binding: ActivityTermsConditiionsBinding
    lateinit var viewModel: TermsAndConditionsVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_terms_conditiions)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_conditiions)
        binding.lifecycleOwner = this
        viewModel = TermsAndConditionsVM()
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
        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    //  AppUtils.ImageWithGlide(terms_image, it.data.get(0).page_image)
                    binding.tvTerms.setText(HtmlCompat.fromHtml(it.data.get(0).page_content, 0))

                } else {
                    // showError(it.message, this)
                }
            }

        })

        if (isNetworkConnected()) {
            viewModel.getTermsAndConditions()
        } else {
            showInternetToast()
        }

    }
}