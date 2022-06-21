package com.myquotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myquotes.network.Quote

class SavedQuotesAdapter(private val quotes: List<Quote>):RecyclerView.Adapter<SavedQuotesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvQuote: TextView = itemView.findViewById(R.id.tv_quote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_saved_quote, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvQuote.text = quotes[position].content
    }

    override fun getItemCount(): Int{
        return quotes.size
    }
}