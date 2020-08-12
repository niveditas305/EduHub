package com.example.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduhub.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_signup.setOnClickListener {
            startActivity(Intent(this,Signup::class.java))
        }
        tv_forgotPassword.setOnClickListener {
            startActivity(Intent(this,ForgotPassword::class.java))
        }

    }
}