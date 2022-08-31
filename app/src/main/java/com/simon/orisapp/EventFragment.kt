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
import com.simon.orisapp.model.Link

class EventFragment : Fragment() {
    private var card1 : CardView? = null
    private var card2 : CardView? = null
    private var name : TextView? = null
    private var loadingBar : ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_event, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(EventListViewModel::class.java)

        loadingBar = root.findViewById(R.id.progress_circular)

        card1 = root.findViewById(R.id.cardView1)
        val date = root.findViewById<TextView>(R.id.date)
        name = root.findViewById(R.id.name)
        val place = root.findViewById<TextView>(R.id.place)

        card2 = root.findViewById(R.id.cardView2)
        val links = root.findViewById<RecyclerView>(R.id.links)

        card1?.visibility = View.GONE
        card2?.visibility = View.GONE
        name?.visibility = View.GONE

        viewModel.getEventDetails().observe(viewLifecycleOwner) {
            if(it.successful) {
                loadingBar?.visibility = View.GONE
                card1?.visibility = View.VISIBLE
                card2?.visibility = View.VISIBLE
                name?.visibility = View.VISIBLE

                //card1-info
                date.text = "Datum: " + it.data?.date
                name?.text = it.data?.name
                place.text = "Místo: " + it.data?.place

                //card2-links
                it.data?.links?.values.let {noNullLinks->
                    links.adapter = LinksViewAdapter(ArrayList<Link?>(noNullLinks))
                    links.layoutManager = LinearLayoutManager(context)
                }
            }else{
                Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
            }
        }
        return root

    }

    override fun onResume() {
        super.onResume()
        card1?.visibility = View.GONE
        card2?.visibility = View.GONE
        name?.visibility = View.GONE
        loadingBar?.visibility = View.VISIBLE
    }
}