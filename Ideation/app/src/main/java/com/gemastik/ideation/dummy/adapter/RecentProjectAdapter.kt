package com.gemastik.ideation.dummy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.ideation.databinding.RvLayoutBinding
import com.gemastik.ideation.dummy.data.Response


class RecentProjectAdapter(private val listReview: ArrayList<Response>) :
    RecyclerView.Adapter<RecentProjectAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvLayoutBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = listReview[position]
        viewHolder.img.setImageResource(item.photo)
        viewHolder.tvName.text = item.name
        viewHolder.tvDate.text = item.date
        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listReview[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount() = listReview.size

    class ViewHolder(binding: RvLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvName
        val img: ImageView = binding.imgStory
        val tvDate: TextView = binding.tvDate
        val btJoin: Button = binding.button
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Response)
    }
}