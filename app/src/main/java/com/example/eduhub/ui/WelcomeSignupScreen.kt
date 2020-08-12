package com.example.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduhub.R
import kotlinx.android.synthetic.main.activity_welcome_signup_screen.*

class WelcomeSignupScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_signup_screen)
        tv_login.setOnClickListener {
       startActivity(Intent(this,LoginActivity::class.java))
        }
        tv_register.setOnClickListener {
startActivity(Intent(this,Signup::class.java))
        }
    }
}