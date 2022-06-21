package com.myquotes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.collections.ArrayList

class TagAdapter(private val tags:ArrayList<String>): RecyclerView.Adapter<TagAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvTag:TextView = itemView.findViewById(R.id.tv_tag)
        val cvTag: MaterialCardView = itemView.findViewById(R.id.cv_tag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = tags[position]
        holder.tvTag.text = tag

        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(100), rnd.nextInt(100), rnd.nextInt(100))
        holder.cvTag.setCardBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return tags.size
    }
}