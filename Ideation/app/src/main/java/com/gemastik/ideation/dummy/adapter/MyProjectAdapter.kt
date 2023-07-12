package com.gemastik.ideation.dummy.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.ideation.databinding.RvMyProjectBinding
import com.gemastik.ideation.dummy.data.MyProject
import com.gemastik.ideation.dummy.data.Response


class MyProjectAdapter(private val listReview: ArrayList<MyProject>) :
    RecyclerView.Adapter<MyProjectAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvMyProjectBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = listReview[position]
        viewHolder.tvName.text = item.name
        viewHolder.tvPm.text = item.pmName
        viewHolder.img.setImageResource(item.projImg)
        viewHolder.pmImg.setImageResource(item.pmImg)
        viewHolder.tvCat.text = item.category
        viewHolder.status.text = item.status

        if (item.status == "Selesai") {
            viewHolder.status.setTextColor(Color.parseColor("#2C62A7"))
        }
    }

    override fun getItemCount() = listReview.size

    class ViewHolder(binding: RvMyProjectBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvName
        val tvPm: TextView = binding.tvPmName
        val img: ImageView = binding.imgStory
        val pmImg: ImageView = binding.imgPm
        val tvCat: TextView = binding.tvCategory
        val status: TextView = binding.tvStatus
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Response)
    }
}