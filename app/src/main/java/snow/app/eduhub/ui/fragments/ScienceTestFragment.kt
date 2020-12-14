package snow.app.eduhub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.ScienceTestsAdapter


class ScienceTestFragment : Fragment() {
    lateinit var rv_sthisweektest:RecyclerView
    lateinit var rv_soldertests:RecyclerView
    lateinit var scienceTestsAdapter: ScienceTestsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_science_test, container, false)


        rv_sthisweektest=view.findViewById<RecyclerView>(R.id.rv_sthisweektest)
        rv_soldertests=view.findViewById<RecyclerView>(R.id.rv_soldertests)


        val  linearLayoutManagertut= LinearLayoutManager(requireContext())
        rv_sthisweektest.layoutManager=linearLayoutManagertut
        scienceTestsAdapter= ScienceTestsAdapter(requireContext())
        rv_sthisweektest.adapter=scienceTestsAdapter



        val  linearLayoutManager= LinearLayoutManager(requireContext())
        rv_soldertests.layoutManager=linearLayoutManager
        scienceTestsAdapter= ScienceTestsAdapter(requireContext())
        rv_soldertests.adapter=scienceTestsAdapter

        return view
    }


}