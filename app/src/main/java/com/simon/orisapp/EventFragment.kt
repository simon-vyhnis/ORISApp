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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventFragment : Fragment() {

    private val originalFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val userFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    private var card1 : CardView? = null
    private var card2 : CardView? = null
    private var card3 : CardView? = null
    private var card4 : CardView? = null
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
        val sport = root.findViewById<TextView>(R.id.sport)
        val discipline = root.findViewById<TextView>(R.id.discipline)
        val org = root.findViewById<TextView>(R.id.org)
        val mapName = root.findViewById<TextView>(R.id.map_name)
        val startTime = root.findViewById<TextView>(R.id.startTime)
        card2 = root.findViewById(R.id.cardView2)
        val links = root.findViewById<RecyclerView>(R.id.links)
        card3 = root.findViewById(R.id.cardView3)
        val docs = root.findViewById<RecyclerView>(R.id.docs)
        card4 = root.findViewById(R.id.cardView4)

        card1?.visibility = View.GONE
        card2?.visibility = View.GONE
        card3?.visibility = View.GONE
        card4?.visibility = View.GONE
        name?.visibility = View.GONE

        viewModel.getEventDetails().observe(viewLifecycleOwner) {
            if (it.successful) {
                loadingBar?.visibility = View.GONE
                card1?.visibility = View.VISIBLE
                card2?.visibility = View.VISIBLE
                card3?.visibility = View.VISIBLE
                card4?.visibility = View.VISIBLE
                name?.visibility = View.VISIBLE

                //card1-info
                date.text = "Datum: " + LocalDate.parse(it.data?.date, originalFormat).format(userFormat)
                name?.text = it.data?.name
                place.text = "Místo: " + it.data?.place
                sport.text = "Sport: "+ it.data?.sport?.name
                discipline.text = "Disciplína: "+ it.data?.discipline?.name
                org.text = "Organizátor: "+ it.data?.org?.name
                mapName.text = "Mapa: "+it.data?.map
                startTime.text = "Start 00: " + it.data?.startTime

                //card2-links
                it.data?.links?.values.let { noNullLinks ->
                    links.adapter = LinksViewAdapter(ArrayList<Link?>(noNullLinks))
                    links.layoutManager = LinearLayoutManager(context)
                }
                //card3-links
                it.data?.docs?.values.let { noNullDocs ->
                    docs.adapter = LinksViewAdapter(ArrayList<Link?>(noNullDocs))
                    docs.layoutManager = LinearLayoutManager(context)
                }

            } else {
                Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
            }
        }
        return root

    }

    override fun onResume() {
        super.onResume()
        card1?.visibility = View.GONE
        card2?.visibility = View.GONE
        card3?.visibility = View.GONE
        card4?.visibility = View.GONE
        name?.visibility = View.GONE
        loadingBar?.visibility = View.VISIBLE
    }
}