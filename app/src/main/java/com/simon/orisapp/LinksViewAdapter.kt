package com.simon.orisapp

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.simon.orisapp.model.Link

class LinksViewAdapter (private val links: List<Link?>) : RecyclerView.Adapter<LinksViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = "<a href='" + links[position]?.url +"'>"+ links[position]?.name +"</a>"
        holder.name.movementMethod = LinkMovementMethod.getInstance()
        holder.name.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    }

    override fun getItemCount(): Int {
        return links.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
    }
}
