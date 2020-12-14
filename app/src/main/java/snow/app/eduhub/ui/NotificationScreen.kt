package snow.app.eduhub.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.snow.beautyasap.ui.home.adapter.NotificationListAdapter
import snow.app.eduhub.R
import snow.app.eduhub.databinding.ActivityNotificationScreenBinding
import snow.app.eduhub.databinding.ActivitySearchScreenBinding
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.NotificationsVm
import snow.app.eduhub.viewmodels.SearchVm

class NotificationScreen : BaseActivity() {
    lateinit var binding: ActivityNotificationScreenBinding
    lateinit var viewModel: NotificationsVm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_notification_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_screen)
        binding.lifecycleOwner = this
        viewModel = NotificationsVm(getUserToken())
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

        viewModel.respDatanotification.observe(this, Observer {
            if (it != null && it.status) {
                dialog.dismiss()
                hideList(false)
                binding.rvNotifications.layoutManager = LinearLayoutManager(this)
                binding.rvNotifications.adapter = NotificationListAdapter(this, it.data)
            } else {
                dialog.dismiss()
                hideList(true)
            }

        })



        if (isNetworkConnected()) {
            viewModel.fetchAllNOtifications()
        } else {
            showInternetToast()
        }
    }


    fun hideList(yes: Boolean) {
        if (yes) {
            binding.rvNotifications.visibility = View.GONE
            binding.tvNoRecord.visibility = View.VISIBLE
        } else {
            binding.rvNotifications.visibility = View.VISIBLE
            binding.tvNoRecord.visibility = View.GONE
        }

    }
}