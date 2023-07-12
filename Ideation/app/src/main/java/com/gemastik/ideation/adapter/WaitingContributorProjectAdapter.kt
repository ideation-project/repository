package com.gemastik.ideation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.ideation.R
import com.gemastik.ideation.databinding.ItemContributorWaitingBinding
import com.gemastik.ideation.response.ContributorsItemWait

class WaitingContributorProjectAdapter:
    ListAdapter<ContributorsItemWait,WaitingContributorProjectAdapter.ViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemContributorWaitingBinding):
    RecyclerView.ViewHolder(binding.root){

        val imgContributor:ImageView = binding.ivContributor
        val nameContributor:TextView = binding.nameContributor
        val btnTolak:Button = binding.btnTolak
        val btnTerima:Button = binding.btnTerimaa
        fun bind(data:ContributorsItemWait){
            binding.ivContributor.setImageResource(R.drawable.holder_person)
            binding.nameContributor.text = data.username
            binding.roleContributor.text = data.role
            binding.btnTolak.setOnClickListener {

            }
            binding.btnTerimaa.setOnClickListener {

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContributorWaitingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){

            holder.imgContributor.setImageResource(R.drawable.holder_person)
            holder.nameContributor.text = data.username
            holder.btnTolak.setOnClickListener {
                onItemClickCallback.onItemTolakClicked(data)
            }
            holder.btnTerima.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
            //holder.bind(data)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ContributorsItemWait)
        fun onItemTolakClicked(data: ContributorsItemWait)

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContributorsItemWait>() {
            override fun areItemsTheSame(
                oldItem: ContributorsItemWait,
                newItem: ContributorsItemWait
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ContributorsItemWait,
                newItem: ContributorsItemWait
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }




}