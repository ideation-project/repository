package com.gemastik.ideation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemastik.ideation.R
import com.gemastik.ideation.response.RecommendationsItem

class RekomenListAdapter(private val listProjectRekomen: List<RecommendationsItem>):
RecyclerView.Adapter<RekomenListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: RecommendationsItem)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgPhoto: ImageView = itemView.findViewById(R.id.img_projectIV)
        val judul: TextView = itemView.findViewById(R.id.TitleProjectTV)
        val kategori: TextView = itemView.findViewById(R.id.tv_Category)
        val date: TextView = itemView.findViewById(R.id.dateTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_latest_project, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listProjectRekomen[position]
        holder.judul.text = item.project.nmProyek
        holder.date.text = item.project.postedAt
        holder.kategori.text = item.project.category.nmKategori
        Glide.with(holder.itemView.context)
            .load(item.project.gambar)
            .into(holder.imgPhoto)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listProjectRekomen[position])
        }


    }

    override fun getItemCount(): Int = listProjectRekomen.size

}