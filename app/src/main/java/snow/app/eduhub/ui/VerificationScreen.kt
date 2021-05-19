package snow.app.eduhub.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import snow.app.eduhub.MainActivity

import kotlinx.android.synthetic.main.activity_verification_screen.*
import snow.app.eduhub.ChangeEmailScreen
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityForgotResetPassBinding
import snow.app.eduhub.databinding.ActivityVerificationScreenBinding
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.util.AppSession
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ResetPasswordVm
import snow.app.eduhub.viewmodels.VerificationVM

class VerificationScreen : BaseActivity() {
    var viewModel: VerificationVM? = null
    lateinit var binding: ActivityVerificationScreenBinding

    var otp: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_verification_screen)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification_screen)
        binding.lifecycleOwner = this
        viewModel = VerificationVM(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.tvEmail.setText(getSession()?.getAppData()?.data?.email)
        viewModel?.email?.set(getSession()?.getAppData()?.data?.email)
        viewModel!!.isLoading.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel!!.isError.observe(this, Observer {
            if (it.isError) {
                showError(it.message, this);
            }

        })
        binding.tvChangeemail.setOnClickListener {
            startActivity(Intent(this, ChangeEmailScreen::class.java))
        }
        firstPinView.addTextChangedListener {
            val sb = StringBuilder()
            sb.append(it)
            otp = sb.toString()

        }

        if (intent.hasExtra("from")) {
            if (isNetworkConnected()) {
                viewModel?.sendOtp()
            } else {
                showInternetToast()
            }
        }





        binding.tvsend.setOnClickListener {


            if (isNetworkConnected()) {
                if (otp.length == 4) {
                    viewModel?.verifyOTP(otp)
                    // startActivity(Intent(this,MainActivity::class.java))
                } else {
                    showToast("Please enter otp sent to your registered email!")
                }
            } else {
                showInternetToast()
            }


        }


        binding.tvResendcode.setOnClickListener {
            if (isNetworkConnected()) {
                viewModel?.sendOtp()
            } else {
                showInternetToast()
            }
        }





        viewModel?.respData?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {


                    if (getSession()?.getAppData() != null) {
                        var data: SignupRes = getSession()?.getAppData()!!
                        data?.data.studentStatus = 1
                        val session: AppSession = AppSession(this)
                        session.saveSession(data)
                    }


                    dialog.dismiss()
                    showToast(it.message)
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    Log.e("statusfalse", "login--")

                    showError(it.message, this)
                }
            }

        })
        viewModel?.respDatasendotp?.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    Log.e("status true", "login--")

                    showToast(it.message)
                } else {
                    Log.e("statusfalse", "login--")

                    showError(it.message, this)
                }
            }

        })

    }

}
