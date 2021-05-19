package snow.app.eduhub.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_enter_number_screen.*
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login.*
import snow.app.eduhub.R
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ForgotPasswordVM

class ForgotPassword : BaseActivity() {

    lateinit var viewModel: ForgotPasswordVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        viewModel = ForgotPasswordVM(getDeviceToken())
        ll_backtologin.setOnClickListener {
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
        tv_send_forgot.setOnClickListener {






            if (ed_email_forgot.text.toString().isEmpty()) {
                showToast("Please enter email")
            } else {

                if (isNetworkConnected()){
                    viewModel.forgotPassword(ed_email_forgot.text.toString())
                }else{
                    showInternetToast()
                }


            }

        }



        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {


                if (it.status) {
                    dialog.dismiss()
                    val intent: Intent = Intent(this, ForgotResetPass::class.java)
                    intent.putExtra("email", it.data)
                    startActivity(intent)
                } else {

                    showError(it.message, this)
                }
            }

        })

    }
}
