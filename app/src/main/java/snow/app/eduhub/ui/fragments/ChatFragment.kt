package snow.app.eduhub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.MessageAdapter


class ChatFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_chat, container, false)


        val recyclerView =  view.findViewById<RecyclerView>(R.id.rv_msgs)
        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = MessageAdapter(requireContext())

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter




        return view
    }


}