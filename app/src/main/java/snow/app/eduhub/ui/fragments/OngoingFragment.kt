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


class OngoingFragment : Fragment() {
lateinit var rvongoing:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_ongoing, container, false)
       /* rvongoing=view.findViewById(R.id.rvongoing)
        val  linearLayoutManagertut= LinearLayoutManager(requireContext())
        rvongoing.layoutManager=linearLayoutManagertut
   val      worksheetAdapter= WorksheetAdapter(requireContext())
        rvongoing.adapter=worksheetAdapter*/







        return view
    }


}