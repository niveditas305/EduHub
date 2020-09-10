package com.example.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eduhub.R
import kotlinx.android.synthetic.main.activity_test_series.*

class TestSeriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_series)
        iv_bac.setOnClickListener {
            onBackPressed()
        }


        tv_submit.setOnClickListener {
            startActivity(Intent(this,TestSummary::class.java))
        }
    }
}