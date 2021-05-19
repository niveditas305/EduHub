package snow.app.eduhub.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_contact_us.*
import snow.app.eduhub.databinding.ActivityContactUsBinding
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.ContactUsVM

class ContactUs : BaseActivity() {
    lateinit var binding: ActivityContactUsBinding
    lateinit var viewModel: ContactUsVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_contact_us)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        binding.lifecycleOwner = this
        viewModel = ContactUsVM(getUserToken())
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

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.tvSend.setOnClickListener {
            if (isNetworkConnected()) {
                viewModel.getContactUsData()
            } else {
                showInternetToast()
            }

        }



        binding.tvContact.setOnClickListener {


            if (viewModel.phonenumber.get().toString().isEmpty()){
                showToast("Not able to contact!")
            }else{
                callNow(viewModel.phonenumber.get().toString())
            }

        }

        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    showToast(it.message)
                    finish()
                } else {
                    Log.e("statusfalse", "login--")
                    showError(it.message, this)
                }
            }

        })

        viewModel.respDatafetchdetails.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                } else {
                    Log.e("statusfalse", "login--")
                    //   showError(it.message, this)
                    dialog.dismiss()
                }
            }

        })



        if (isNetworkConnected()) {
            viewModel.fetchAdminDetails()
        } else {
            showInternetToast()
        }
    }

    fun callNow(number: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + number)
        startActivity(dialIntent)
    }
}