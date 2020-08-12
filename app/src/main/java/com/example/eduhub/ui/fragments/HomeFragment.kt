package com.example.eduhub.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.eduhub.R
import com.example.eduhub.ui.ContinueDetails
import com.example.eduhub.ui.adapter.HomeTopPickAdapter
import com.example.eduhub.ui.adapter.MyViewPagerAdapter
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {
    var image_resources1 = intArrayOf(R.drawable.ic_img1, R.drawable.ic_img1, R.drawable.ic_img1, R.drawable.ic_img1, R.drawable.ic_img1,
        R.drawable.ic_img1)
    lateinit var pager:ViewPager
lateinit var rv_top_home:RecyclerView
lateinit var dots:TabLayout
lateinit var tv_main_two:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home2, container, false)


        val  linearLayoutManager= LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)

        rv_top_home=view.findViewById(R.id.rv_top_home)
        pager=view.findViewById(R.id.pager)
        dots=view.findViewById(R.id.dots)
        tv_main_two=view.findViewById(R.id.tv_main_two)
        rv_top_home.layoutManager=linearLayoutManager
      val  homeTopPickAdapter= HomeTopPickAdapter(requireContext())
        rv_top_home.adapter=homeTopPickAdapter


        val adapter = MyViewPagerAdapter(requireContext(),image_resources1)
        pager.adapter = adapter

        dots.setupWithViewPager(pager, true) // <- magic here


        tv_main_two.setOnClickListener {
            startActivity(Intent(requireContext(), ContinueDetails::class.java))
        }
        return view
    }


}