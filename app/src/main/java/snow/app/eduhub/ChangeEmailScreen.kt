package snow.app.eduhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import snow.app.eduhub.databinding.ActivityChangeEmailScreenBinding
import snow.app.eduhub.ui.VerificationScreen
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.util.AppSession

import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.util.OnTokenExpired
import snow.app.eduhub.viewmodels.ChangeEmailVm

class ChangeEmailScreen : BaseActivity(), OnTokenExpired.OnTokenExpiredListener {


    var viewModel: ChangeEmailVm? = null
    lateinit var binding: ActivityChangeEmailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    setContentView(R.layout.activity_change_email_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_email_screen)
        binding.lifecycleOwner = this
        viewModel = ChangeEmailVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        OnTokenExpired.setOnTokenListener(this)
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }




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


        viewModel?.respDatasendotp?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {


                    Log.e("status true", "login--")
                    dialog.dismiss()

                    showToast(it.message)
                    startActivity(Intent(this, VerificationScreen::class.java))
                } else {
                    Log.e("statusfalse", "login--")

                    showError(it.message, this)
                }
            }

        })
        binding.btnUpdate.setOnClickListener {
            if (isNetworkConnected()) {
                viewModel?.sendOtp()

                if (getSession()?.getAppData() != null) {
                    var data: SignupRes = getSession()?.getAppData()!!
                    data?.data.email = binding.edEmail.text.toString()
                    val session: AppSession = AppSession(this)
                    session.saveSession(data)
                }
            } else {
                showInternetToast()
            }
        }

    }

    override fun onTokenExpiredListener() {
        viewModel!!.isLoading.postValue(false)
        showTokenError()
    }
}


