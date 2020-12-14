package snow.app.eduhub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.CompletedWorksheetAdapter


class CompletedFragment : Fragment() {

    lateinit var rv_completed:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

val view= inflater.inflate(R.layout.fragment_completed, container, false)

        rv_completed=view.findViewById(R.id.rv_completed)
        val  linearLayoutManagertut= LinearLayoutManager(requireContext())
        rv_completed.layoutManager=linearLayoutManagertut
        val      worksheetAdapter= CompletedWorksheetAdapter(requireContext())
        rv_completed.adapter=worksheetAdapter
        return view
    }


}