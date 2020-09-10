package com.example.eduhub.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.eduhub.R
import com.example.eduhub.ui.ContinueDetails


class LearnFragment : Fragment() {
lateinit var  tv_main_two:TextView
lateinit var  tv_one:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_learn, container, false)

        tv_main_two=view.findViewById(R.id.tv_main_two)
        tv_one=view.findViewById(R.id.tv_one)
        tv_main_two.setOnClickListener {
            startActivity(Intent(requireContext(),ContinueDetails::class.java))
        }
        tv_one.setOnClickListener {
            startActivity(Intent(requireContext(),ContinueDetails::class.java))
        }
        return view
    }


}