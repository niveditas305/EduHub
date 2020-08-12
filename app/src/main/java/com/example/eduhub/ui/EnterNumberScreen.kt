package com.example.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduhub.R
import kotlinx.android.synthetic.main.activity_enter_number_screen.*

class EnterNumberScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_number_screen)
        tv_send.setOnClickListener {
            startActivity(Intent(this,VerificationScreen::class.java))
        }
    }
}