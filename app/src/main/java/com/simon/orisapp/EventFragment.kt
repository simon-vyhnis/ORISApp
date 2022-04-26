package com.simon.orisapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.orisapp.model.Link

class EventFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_event, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(EventListViewModel::class.java)

        val card1 = root.findViewById<CardView>(R.id.cardView1)
        val date = root.findViewById<TextView>(R.id.date)
        val name = root.findViewById<TextView>(R.id.name)
        val place = root.findViewById<TextView>(R.id.place)
        val sport = root.findViewById<TextView>(R.id.sport)
        val discipline = root.findViewById<TextView>(R.id.discipline)
        val org = root.findViewById<TextView>(R.id.org)
        val mapName = root.findViewById<TextView>(R.id.map_name)
        val startTime = root.findViewById<TextView>(R.id.startTime)

        val card2 = root.findViewById<CardView>(R.id.cardView2)
        val links = root.findViewById<RecyclerView>(R.id.links)

        val card3 = root.findViewById<CardView>(R.id.cardView3)
        val docs = root.findViewById<RecyclerView>(R.id.docs)

        val card4 = root.findViewById<CardView>(R.id.cardView4)

        card1.visibility = View.GONE
        card2.visibility = View.GONE
        card3.visibility = View.GONE
        card4.visibility = View.GONE
        name.visibility = View.GONE

        viewModel.getEventDetails().observe(viewLifecycleOwner) {
            if (it.successful) {
                root.findViewById<ProgressBar>(R.id.progress_circular).visibility = View.GONE
                card1.visibility = View.VISIBLE
                card2.visibility = View.VISIBLE
                card3.visibility = View.VISIBLE
                card4.visibility = View.VISIBLE
                name.visibility = View.VISIBLE

                //card1-info
                date.text = "Datum: " + it.data?.date
                name.text = it.data?.name
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
                val map = view?.findViewById<WebView>(R.id.map)
                map?.doOnLayout {view->
                    loadMap(root, it.data?.longitude, it.data?.latitude)
                }

            } else {
                Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
            }
        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun loadMap(view: View, latitude: Float?, longitude: Float?) {
        print("Lat: $latitude Long: $longitude")
        val map = view.findViewById<WebView>(R.id.map)
        val html =
            "<!doctype html><html><head><script src=\"https://api.mapy.cz/loader.js\"></script><script>Loader.load()</script></head>" +
                    "<body><div id=\"map\" style=\"width:${map.width}px; height:${map.height}px;\"></div><script type=\"text/javascript\">" +
                    "var stred = SMap.Coords.fromWGS84(${latitude}, ${longitude});" +
                    "var map = new SMap(JAK.gel(\"map\"), stred, 12);" +
                    "var layer = new SMap.Layer.Marker();" +
                    "map.addLayer(layer);" +
                    "layer.enable();" +
                    //"var options = {};" + "var marker = new SMap.Marker(stred, \"myMarker\", options);" + "layer.addMarker(marker);"
                    "map.addDefaultLayer(SMap.DEF_BASE).enable();" +
                    "map.addDefaultControls();</script></body></html>"
        val mime = "text/html"
        val encoding = "utf-8"
        map.settings.javaScriptEnabled = true
        map.settings.useWideViewPort = true
        map.settings.loadWithOverviewMode = true
        map.loadDataWithBaseURL(null, html, mime, encoding, null)
        print("h: " + map.height + "w: " + map.width)
    }
}