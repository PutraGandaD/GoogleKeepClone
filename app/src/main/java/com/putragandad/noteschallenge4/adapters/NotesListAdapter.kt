package com.putragandad.noteschallenge4.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.putragandad.noteschallenge4.R
import com.putragandad.noteschallenge4.data.Notes

class NotesListAdapter(private val dataSet: List<Notes>, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNotesTitle : TextView = view.findViewById(R.id.tv_notes_title_preview)
        val tvNotesContent : TextView = view.findViewById(R.id.tv_notes_content_preview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_notes_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getData = dataSet[position] // get data from the current position

        holder.tvNotesTitle.text = getData.title
        holder.tvNotesContent.text = getData.notesContent

        holder.itemView.setOnClickListener {
            itemClickListener.onNotesClicked(getData)
        }
    }

}

interface OnItemClickListener {
    fun onNotesClicked(notes: Notes)
    fun onDelete(notes: Notes)
}