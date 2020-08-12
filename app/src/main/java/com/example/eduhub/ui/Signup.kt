package com.example.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduhub.R
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        tvlogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        tv_signup.setOnClickListener {

            startActivity(Intent(this,EnterNumberScreen::class.java))
        }
    }
}