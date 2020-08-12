package com.example.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eduhub.R
import com.example.eduhub.ui.adapter.ChaptersAdapter
import com.example.eduhub.ui.adapter.HomeTopPickAdapter
import com.example.eduhub.ui.adapter.TutorialsAdapter
import kotlinx.android.synthetic.main.activity_continue_detail.*
import kotlinx.android.synthetic.main.content_main.*

class ContinueDetails : AppCompatActivity() {
lateinit var chaptersAdapter: ChaptersAdapter
lateinit var tutorialsAdapter: TutorialsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue_detail)

        ivback.setOnClickListener {
            onBackPressed()
        }

        val  linearLayoutManager= GridLayoutManager(this,
            2)
        rv_tutorials.layoutManager=linearLayoutManager
        tutorialsAdapter= TutorialsAdapter(this)
        rv_tutorials.adapter=tutorialsAdapter



        val  linearLayoutManagertut= LinearLayoutManager(this)
        rv_chapters.layoutManager=linearLayoutManagertut
        chaptersAdapter= ChaptersAdapter(this)
        rv_chapters.adapter=chaptersAdapter
    }
}