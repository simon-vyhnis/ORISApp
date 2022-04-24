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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.orisapp.model.Link

class EventFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_event, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(EventListViewModel::class.java)

        val card1 = root.findViewById<CardView>(R.id.cardView1)
        val date = root.findViewById<TextView>(R.id.date)
        val name = root.findViewById<TextView>(R.id.name)
        val place = root.findViewById<TextView>(R.id.place)

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
            if(it.successful) {
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

                //card2-links
                it.data?.links?.values.let {noNullLinks->
                    links.adapter = LinksViewAdapter(ArrayList<Link?>(noNullLinks))
                    links.layoutManager = LinearLayoutManager(context)
                }
                //card3-links
                it.data?.docs?.values.let {noNullDocs->
                    docs.adapter = LinksViewAdapter(ArrayList<Link?>(noNullDocs))
                    docs.layoutManager = LinearLayoutManager(context)
                }
            }else{
                Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
            }
        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //card4-map
        val map = view.findViewById<WebView>(R.id.map)
        map.doOnLayout {
            val html =
                "<!doctype html><html><head><script src=\"https://api.mapy.cz/loader.js\"></script><script>Loader.load()</script></head>" +
                        "<body><div id=\"mapa\" style=\"width:${map.width}px; height:${map.height}px;\"></div><script type=\"text/javascript\">" +
                        "var stred = SMap.Coords.fromWGS84(14.41, 50.08);" +
                        "var mapa = new SMap(JAK.gel(\"mapa\"), stred, 10);" +
                        "mapa.addDefaultLayer(SMap.DEF_BASE).enable();" +
                        "mapa.addDefaultControls();</script></body></html>"
            val mime = "text/html"
            val encoding = "utf-8"
            map.settings.javaScriptEnabled = true
            map.settings.useWideViewPort = true;
            map.settings.loadWithOverviewMode = true;
            map.loadDataWithBaseURL(null, html, mime, encoding, null)
            print("h: " + map.height + "w: " + map.width)
        }
    }
}