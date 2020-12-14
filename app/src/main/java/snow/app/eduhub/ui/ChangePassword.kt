package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_change_password.*
import snow.app.eduhub.databinding.ActivityChangePasswordBinding
import snow.app.eduhub.databinding.ActivityContactUsBinding
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChangePasswordVM
import snow.app.eduhub.viewmodels.ContactUsVM

class ChangePassword : BaseActivity() {

    lateinit var binding: ActivityChangePasswordBinding
    lateinit var viewModel: ChangePasswordVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_change_password)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        binding.lifecycleOwner = this
        viewModel = ChangePasswordVM(getUserToken())
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

        binding.tvUpdate.setOnClickListener {
            if (isNetworkConnected()) {
                viewModel.changePassword()
            } else {
                showInternetToast()
            }
        }

        binding.ivInviCurrent.setOnClickListener(View.OnClickListener {
            binding.edCurrent.setTransformationMethod(PasswordTransformationMethod())
            binding.ivViCurrent.setVisibility(View.VISIBLE)
            binding.ivInviCurrent.setVisibility(View.GONE)

            binding.edCurrent.setSelection(binding.edCurrent.getText().toString().length);
        })

        binding.ivViCurrent.setOnClickListener(View.OnClickListener {

            binding.edCurrent.setTransformationMethod(HideReturnsTransformationMethod.getInstance())

            binding.ivInviCurrent.setVisibility(View.VISIBLE)
            binding.ivViCurrent.setVisibility(View.GONE)
            binding.edCurrent.setSelection(binding.edCurrent.getText().toString().length);
        })




        binding.ivInvisible.setOnClickListener(View.OnClickListener {
            binding.edPass.setTransformationMethod(PasswordTransformationMethod())
            binding.ivVisible.setVisibility(View.VISIBLE)
            binding.ivInvisible.setVisibility(View.GONE)

            binding.edPass.setSelection(binding.edPass.getText().toString().length);
        })

        binding.ivVisible.setOnClickListener(View.OnClickListener {

            binding.edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance())

            binding.ivInvisible.setVisibility(View.VISIBLE)
            binding.ivVisible.setVisibility(View.GONE)
            binding.edPass.setSelection(binding.edPass.getText().toString().length);
        })
        binding.ivInvisibleReenter.setOnClickListener(View.OnClickListener {
            binding.edReenter.setTransformationMethod(PasswordTransformationMethod())
            binding.ivVisibleReenter.setVisibility(View.VISIBLE)
            binding.ivInvisibleReenter.setVisibility(View.GONE)

            binding.edReenter.setSelection(binding.edReenter.getText().toString().length);
        })

        binding.ivVisibleReenter.setOnClickListener(View.OnClickListener {

            binding.edReenter.setTransformationMethod(HideReturnsTransformationMethod.getInstance())

            binding.ivInvisibleReenter.setVisibility(View.VISIBLE)
            binding.ivVisibleReenter.setVisibility(View.GONE)
            binding.edReenter.setSelection(binding.edReenter.getText().toString().length);
        })
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
    }
}