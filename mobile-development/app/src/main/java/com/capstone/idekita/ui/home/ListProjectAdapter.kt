package com.capstone.idekita.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.idekita.R
import com.capstone.idekita.model.wisataEntity

class ListProjectAdapter(private val listwisata: List<wisataEntity>) :
    RecyclerView.Adapter<ListProjectAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: wisataEntity)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val imgPhoto: ImageView = itemView.findViewById(R.id.img_projectIV)
        val judul: TextView = itemView.findViewById(R.id.TitleProjectTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_latest_project, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val (id, name, photo, lokasi, desc) = listwisata[position]
        holder.judul.text = name
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.imgPhoto)
        holder.itemView.setOnClickListener {

            val bundle = Bundle()


            onItemClickCallback.onItemClicked(listwisata[position])
        }

    }

    override fun getItemCount(): Int = listwisata.size
}