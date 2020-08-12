package com.example.eduhub.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.eduhub.R
import com.example.eduhub.ui.*


class SettingFragment : Fragment() {

lateinit var  change_pwd:TextView
lateinit var  contactus:TextView
lateinit var  tv_about:TextView
lateinit var  tv_privacy:TextView
lateinit var  tv_terms:TextView
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_setting, container, false)
        change_pwd=view.findViewById(R.id.change_pwd)
        contactus=view.findViewById(R.id.contactus)
        tv_about=view.findViewById(R.id.tv_about)
        tv_privacy=view.findViewById(R.id.tv_privacy)
         tv_terms=view.findViewById(R.id.tv_terms)
        change_pwd.setOnClickListener {
            startActivity(Intent(requireContext(),ChangePassword::class.java))
        }
        contactus.setOnClickListener {
            startActivity(Intent(requireContext(),ContactUs::class.java))
        }
        tv_privacy.setOnClickListener {
            startActivity(Intent(requireContext(),PrivacyScreen::class.java))
        }
        tv_about.setOnClickListener {
            startActivity(Intent(requireContext(),AboutScreen::class.java))
        }
         tv_terms.setOnClickListener {
            startActivity(Intent(requireContext(),TermsConditiions::class.java))
        }
        return view
    }


}