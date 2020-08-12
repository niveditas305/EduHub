package com.example.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduhub.R
import kotlinx.android.synthetic.main.activity_terms_conditiions.*
import kotlinx.android.synthetic.main.activity_test_series.*

class TermsConditiions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditiions)

        iv_back.setOnClickListener {
            onBackPressed()
        }
    }
}