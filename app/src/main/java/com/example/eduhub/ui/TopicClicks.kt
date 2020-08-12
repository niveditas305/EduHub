package com.example.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.eduhub.R
import com.example.eduhub.ui.adapter.TutorialsAdapter
import com.example.eduhub.ui.adapter.WorksheetPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_continue_detail.*
import kotlinx.android.synthetic.main.activity_topic_clicks.*

class TopicClicks : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_clicks)


        val  linearLayoutManager= GridLayoutManager(this,
            2)
        rvvideos.layoutManager=linearLayoutManager
       val  tutorialsAdapter= TutorialsAdapter(this)
        rvvideos.adapter=tutorialsAdapter

 tb_w.addTab(tb_w.newTab().setText( getString(R.string.all)))
        tb_w.addTab(tb_w.newTab().setText(  getString(R.string.ongoing)))
        tb_w.addTab(tb_w.newTab().setText(  getString(R.string.completed)))
        tb_w.tabGravity = TabLayout.GRAVITY_FILL

        val worksheetPagerAdapter = WorksheetPagerAdapter(supportFragmentManager)
        vp_worksheet.adapter = worksheetPagerAdapter

        vp_worksheet.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tb_w))
        tb_w.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vp_worksheet.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}