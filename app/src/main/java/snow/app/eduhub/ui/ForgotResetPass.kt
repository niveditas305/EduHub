package snow.app.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityForgotResetPassBinding
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ResetPasswordVm


class ForgotResetPass : BaseActivity()/*, OnTokenExpired.OnTokenExpiredListener*/ {
    var viewModel: ResetPasswordVm? = null
    lateinit var binding: ActivityForgotResetPassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_forgot_reset_pass)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_reset_pass)
        binding.lifecycleOwner = this
        viewModel = ResetPasswordVm(getDeviceToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        /*     OnTokenExpired.setOnTokenListener(this)*/
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }


        //get email from intent
        if (intent.hasExtra("email")) {
            viewModel?.email?.set(intent.getStringExtra("email"))
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


        binding.tvreset.setOnClickListener {
            if (isNetworkConnected()){
                viewModel?.onResetPassClick()
            }else{
                showError("No Internet Connection !!",this)
            }
        }


        viewModel?.respData?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {


                if (it.status) {
                    dialog.dismiss()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                } else {

                    showError(it.message, this)
                }
            }

        })
    }
/*
    override fun onTokenExpiredListener() {
        viewModel!!.isLoading.postValue(false)
        showTokenError()
    }*/
}