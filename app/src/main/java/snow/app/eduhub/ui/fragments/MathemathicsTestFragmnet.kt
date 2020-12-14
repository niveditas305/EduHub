package snow.app.eduhub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.TestsAdapter

class MathemathicsTestFragmnet : Fragment() {

lateinit var rv_thisweektest:RecyclerView
lateinit var rv_oldertests:RecyclerView
lateinit var testsAdapter: TestsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_mathemathics_test_fragmnet, container, false)

        rv_thisweektest=view.findViewById<RecyclerView>(R.id.rv_thisweektest)
        rv_oldertests=view.findViewById<RecyclerView>(R.id.rv_oldertests)


        val  linearLayoutManagertut= LinearLayoutManager(requireContext())
        rv_thisweektest.layoutManager=linearLayoutManagertut
        testsAdapter= TestsAdapter(requireContext())
        rv_thisweektest.adapter=testsAdapter



        val  linearLayoutManager= LinearLayoutManager(requireContext())
        rv_oldertests.layoutManager=linearLayoutManager
        testsAdapter= TestsAdapter(requireContext())
        rv_oldertests.adapter=testsAdapter
        return view
    }


}