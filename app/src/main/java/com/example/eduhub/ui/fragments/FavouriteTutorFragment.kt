package com.example.eduhub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eduhub.R
import com.example.eduhub.ui.adapter.FavTutAdapter
import com.example.eduhub.ui.adapter.TutorialsAdapter
import kotlinx.android.synthetic.main.activity_continue_detail.*


class FavouriteTutorFragment : Fragment() {
lateinit var rv_favtut:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_favourite_tutor, container, false)
        rv_favtut=view.findViewById(R.id.rv_favtut)
        val  linearLayoutManager= GridLayoutManager(requireContext(),
            2)
        rv_favtut.layoutManager=linearLayoutManager
       val favTutAdapter= FavTutAdapter(requireContext())
        rv_favtut.adapter=favTutAdapter

        return view
    }


}