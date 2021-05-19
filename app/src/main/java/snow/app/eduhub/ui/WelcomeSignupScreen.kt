package snow.app.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_welcome_signup_screen.*
import snow.app.eduhub.databinding.ActivityVerificationScreenBinding
import snow.app.eduhub.databinding.ActivityWelcomeSignupScreenBinding

class WelcomeSignupScreen : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeSignupScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_signup_screen)
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
    }
}