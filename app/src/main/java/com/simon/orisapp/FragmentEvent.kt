package com.simon.orisapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentEvent : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_event, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(EventListViewModel::class.java)

        viewModel.getEventDetails().observe(viewLifecycleOwner) {
            root.findViewById<TextView>(R.id.date).text  = "Datum: "+ it.data.date
            root.findViewById<TextView>(R.id.name).text  = it.data.name
            root.findViewById<TextView>(R.id.place).text  = "Místo: "+it.data.place
        }
        return root

    }
}