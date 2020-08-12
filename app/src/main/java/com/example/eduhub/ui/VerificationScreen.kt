package com.example.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduhub.MainActivity
import com.example.eduhub.R
import kotlinx.android.synthetic.main.activity_verification_screen.*

class VerificationScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_screen)
        tvsend.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}