package com.example.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileListAdapter (private val fileNames: List<String>,
                       private val onItemClick: (String) -> Unit): RecyclerView.Adapter<FileListAdapter.FileViewHolder>() {

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.fileNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileName = fileNames[position]
        holder.fileNameTextView.text = fileName

        holder.itemView.setOnClickListener {
            onItemClick(fileName)
        }
    }
    override fun getItemCount(): Int = fileNames.size
}
