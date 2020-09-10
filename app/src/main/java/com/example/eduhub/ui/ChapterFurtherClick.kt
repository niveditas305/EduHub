package com.example.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eduhub.R
import com.example.eduhub.ui.adapter.ChapterDetailssAdapter
import com.example.eduhub.ui.adapter.ChaptersAdapter
import kotlinx.android.synthetic.main.activity_continue_detail.*
import kotlinx.android.synthetic.main.activity_topic_click_screen.*

class ChapterFurtherClick : AppCompatActivity() {
    lateinit var  chapterDetailssAdapter: ChapterDetailssAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_click_screen)
        iv_back.setOnClickListener {
            onBackPressed()
        }

        val  linearLayoutManagertut= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_one.layoutManager=linearLayoutManagertut
        chapterDetailssAdapter= ChapterDetailssAdapter(this)
        rv_one.adapter=chapterDetailssAdapter



        val  linearLayoutManagerttwo= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvtwo.layoutManager=linearLayoutManagerttwo
        chapterDetailssAdapter= ChapterDetailssAdapter(this)
        rvtwo.adapter=chapterDetailssAdapter


        val  linearLayoutManagertthree= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvthree.layoutManager=linearLayoutManagertthree
        chapterDetailssAdapter= ChapterDetailssAdapter(this)
        rvthree.adapter=chapterDetailssAdapter


        val  linearLayoutManagertfour= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_four.layoutManager=linearLayoutManagertfour
        chapterDetailssAdapter= ChapterDetailssAdapter(this)
        rv_four.adapter=chapterDetailssAdapter
    }
 }