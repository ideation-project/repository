package com.capstone.idekita.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.idekita.R
import com.capstone.idekita.response.CategoriesItem
import com.capstone.idekita.response.ContributorsItem

class ListKategoriAdapter(val listKategori:List<CategoriesItem>):RecyclerView.Adapter<ListKategoriAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback

    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val tvKategori:TextView = view.findViewById(R.id.Kategori_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_kategori,parent,false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvKategori.text = listKategori[position].nmKategori
        holder.tvKategori.setOnClickListener { onItemClickCallback.onItemClicked(listKategori[position]) }
    }

    override fun getItemCount(): Int = listKategori.size

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoriesItem)
    }

}