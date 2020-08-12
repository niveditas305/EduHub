package com.example.eduhub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eduhub.R
import com.example.eduhub.ui.adapter.ChaptersAdapter
import com.example.eduhub.ui.adapter.WorksheetAdapter
import kotlinx.android.synthetic.main.activity_continue_detail.*


class AllFragment : Fragment() {
lateinit var rv_all:RecyclerView
lateinit var worksheetAdapter: WorksheetAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     val view=inflater.inflate(R.layout.fragment_all, container, false)
        rv_all=view.findViewById(R.id.rv_all)


        val  linearLayoutManager= LinearLayoutManager(requireContext())
        rv_all.layoutManager=linearLayoutManager
        worksheetAdapter= WorksheetAdapter(requireContext())




        rv_all.adapter=worksheetAdapter
        return view
    }


}