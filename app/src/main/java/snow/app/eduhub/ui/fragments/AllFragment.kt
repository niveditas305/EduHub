package snow.app.eduhub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.WorksheetAdapter


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


    /*    val  linearLayoutManager= LinearLayoutManager(requireContext())
        rv_all.layoutManager=linearLayoutManager
        worksheetAdapter= WorksheetAdapter(requireContext())*/




        rv_all.adapter=worksheetAdapter
        return view
    }


}