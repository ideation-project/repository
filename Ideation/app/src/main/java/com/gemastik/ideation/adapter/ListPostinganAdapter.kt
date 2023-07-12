package com.gemastik.ideation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.ideation.R
import com.gemastik.ideation.model.postingan

class ListPostinganAdapter(private val listPostingan:ArrayList<postingan>):RecyclerView.Adapter<ListPostinganAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName:TextView = itemView.findViewById(R.id.creatorPostTV)
        val tvDesc:TextView = itemView.findViewById(R.id.descPost)
        val photo:ImageView = itemView.findViewById(R.id.ivPost)
        val btnKomen:ImageButton = itemView.findViewById(R.id.btnKomen)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =  LayoutInflater.from(parent.context).inflate(R.layout.item_postingan,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val item = listPostingan[position]
        holder.tvName.text = item.nama
        holder.tvDesc.text = item.deskripsi
        holder.photo.setImageResource(item.photo)
        holder.btnKomen.setOnClickListener {

        }

    }

    override fun getItemCount(): Int  = listPostingan.size
}