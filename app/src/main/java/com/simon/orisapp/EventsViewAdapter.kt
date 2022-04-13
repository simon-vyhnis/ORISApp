package com.simon.orisapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.simon.orisapp.model.Event
import com.simon.orisapp.model.EventList

class EventsViewAdapter(private val events: EventList, private val context:Context, private val viewModel: EventListViewModel) : RecyclerView.Adapter<EventsViewAdapter.ViewHolder>() {
    private val list = events.data.toList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].second.name
        holder.date.text = list[position].second.date
        if(position%2 == 0){
            holder.itemView.setBackgroundColor(context.getColor(R.color.grey))
        }else{
            holder.itemView.setBackgroundColor(context.getColor(R.color.white))
        }
        holder.itemView.setOnClickListener {
            viewModel.selectedEventId = list[position].second.id
            it.findNavController().navigate(R.id.fragmentEvent)
        }
    }

    override fun getItemCount(): Int {
        return events.data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var date: TextView = itemView.findViewById(R.id.date)
    }
}