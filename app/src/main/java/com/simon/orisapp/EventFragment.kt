package com.simon.orisapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_event, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(EventListViewModel::class.java)

        val date = root.findViewById<TextView>(R.id.date)
        val name = root.findViewById<TextView>(R.id.name)
        val place = root.findViewById<TextView>(R.id.place)
        val card1 = root.findViewById<CardView>(R.id.cardView1)
        card1.visibility = View.GONE
        name.visibility = View.GONE

        viewModel.getEventDetails().observe(viewLifecycleOwner) {
            if(it.successful) {
                root.findViewById<ProgressBar>(R.id.progress_circular).visibility = View.GONE
                card1.visibility = View.VISIBLE
                name.visibility = View.VISIBLE

                date.text = "Datum: " + it.data?.date
                name.text = it.data?.name
                place.text = "Místo: " + it.data?.place
            }else{
                Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
            }
        }
        return root

    }
}