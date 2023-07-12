package com.gemastik.ideation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemastik.ideation.R
import com.gemastik.ideation.databinding.RvMyProjectBinding
import com.gemastik.ideation.response.ProjectsItem
import com.gemastik.ideation.ui.PmDetailSide.PmDetailProjectActivity

class ProjectPagingAdapter : PagingDataAdapter<ProjectsItem, ProjectPagingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: RvMyProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ProjectsItem) {
            binding.tvName.text = data.nmProyek
            binding.tvPmName.text = data.creator
            binding.isiCategory.text = data.category.nmKategori
            binding.tvStatus.text = data.status
            binding.imgPm.setImageResource(R.drawable.holder_person)
            Glide.with(itemView.context)
                .load(data.gambar)
                .into(binding.imgStory)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context,PmDetailProjectActivity::class.java)
                intent.putExtra(PmDetailProjectActivity.EXTRA_DATA,data)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = RvMyProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProjectsItem>() {
            override fun areItemsTheSame(oldItem: ProjectsItem, newItem: ProjectsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProjectsItem, newItem: ProjectsItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}