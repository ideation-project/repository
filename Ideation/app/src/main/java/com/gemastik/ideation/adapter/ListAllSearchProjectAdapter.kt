package com.gemastik.ideation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemastik.ideation.R
import com.gemastik.ideation.databinding.RvMyProjectBinding

import com.gemastik.ideation.response.ProjectsItem
import com.gemastik.ideation.ui.PmDetailSide.PmDetailProjectActivity

class ListAllSearchProjectAdapter: ListAdapter<ProjectsItem,ListAllSearchProjectAdapter.ViewHolder>(DIFF_CALLBACK){


    class ViewHolder(private val binding: RvMyProjectBinding): RecyclerView.ViewHolder(binding.root) {
       fun bind(data : ProjectsItem){
           binding.tvName.text = data.nmProyek
           binding.tvPmName.text = data.creator
           binding.imgPm.setImageResource(R.drawable.holder_person)
           binding.tvStatus.text = data.status
           binding.isiCategory.text = data.category.nmKategori
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :ViewHolder{
        val binding = RvMyProjectBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder:ViewHolder,position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }

    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProjectsItem>() {
            override fun areItemsTheSame(oldItem: ProjectsItem, newItem: ProjectsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProjectsItem, newItem: ProjectsItem): Boolean {
                return oldItem.id == newItem.id
            }


        }
    }

}