package com.example.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduhub.R
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.activity_terms_conditiions.*

class ContactUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}