package com.gemastik.ideation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemastik.ideation.databinding.ItemRowLatestProjectBinding
import com.gemastik.ideation.response.RecommendationsItem
import com.gemastik.ideation.ui.detailProject.DetailProjectActivity

class ListAllRecomendationProjectAdapter :ListAdapter<RecommendationsItem,ListAllRecomendationProjectAdapter.ViewHolder>(DIFF_CALLBACK){

    class ViewHolder(private val binding: ItemRowLatestProjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RecommendationsItem){
            binding.TitleProjectTV.text = data.project.nmProyek
            binding.tvCategory.text = data.project.category.nmKategori
            binding.dateTV.text = data.project.postedAt
            Glide.with(itemView.context)
                .load(data.project.gambar)
                .into(binding.imgProjectIV)
            itemView.setOnClickListener {

                val intent = Intent(itemView.context,DetailProjectActivity::class.java)
                intent.putExtra(DetailProjectActivity.EXTRA_DATA,data)
                //intent.putExtra(PmDetailProjectActivity.REKOM,true)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowLatestProjectBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = getItem(position)
        if(data != null){
            holder.bind(data)
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecommendationsItem>() {
            override fun areItemsTheSame(
                oldItem: RecommendationsItem,
                newItem: RecommendationsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RecommendationsItem,
                newItem: RecommendationsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }


        }
    }
}