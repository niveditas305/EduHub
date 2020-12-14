package snow.app.eduhub.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import snow.app.eduhub.R
import snow.app.eduhub.ui.QuestionBankCategoryScreen


class QuestionSubjectsFragemnt : Fragment() {
lateinit var  tv_main_two:LinearLayout
lateinit var  tv_one:LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_questionsubject, container, false)

        tv_main_two=view.findViewById(R.id.tv_main_two)
        tv_one=view.findViewById(R.id.tv_one)
        tv_main_two.setOnClickListener {
            startActivity(Intent(requireContext(),QuestionBankCategoryScreen::class.java))
        }
        tv_one.setOnClickListener {
            startActivity(Intent(requireContext(),QuestionBankCategoryScreen::class.java))
        }
        return view
    }


}