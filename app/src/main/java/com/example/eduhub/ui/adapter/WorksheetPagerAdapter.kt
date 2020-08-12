package com.example.eduhub.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.eduhub.ui.fragments.AllFragment
import com.example.eduhub.ui.fragments.CompletedFragment
import com.example.eduhub.ui.fragments.OngoingFragment


class WorksheetPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return AllFragment()
            1 -> return OngoingFragment()
            2 -> return CompletedFragment()

        }
        return AllFragment()
    }



    override fun getCount(): Int {
        return 3
    }



}