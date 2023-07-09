package com.capstone.idekita.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.idekita.R
import com.capstone.idekita.databinding.ItemContributorBinding
import com.capstone.idekita.response.ContributorsItem
import com.capstone.idekita.response.DContributorsItem
import com.capstone.idekita.response.DProjectsItem
import com.capstone.idekita.ui.myProject.MyProjectAdapter

class ContributorProjectAdapter: ListAdapter<ContributorsItem, ContributorProjectAdapter.ViewHolder>(DIFF_CALLBACK){
    class ViewHolder(private val binding: ItemContributorBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bind(data: ContributorsItem){
            binding.ivContributor.setImageResource(R.drawable.holder_person)
            binding.nameContributor.text = data.username
            binding.roleContributor.text = data.role
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemContributorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContributorsItem>() {
            override fun areItemsTheSame(
                oldItem: ContributorsItem,
                newItem: ContributorsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ContributorsItem,
                newItem: ContributorsItem
            ): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }

}
