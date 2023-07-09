package com.capstone.idekita.ui.chatroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.idekita.R
import com.capstone.idekita.databinding.RvChatbubbleBinding
import com.capstone.idekita.databinding.RvMyProjectBinding
import com.capstone.idekita.response.DProjectsItem
import com.capstone.idekita.response.Message
import com.capstone.idekita.response.MessagesItem
import com.capstone.idekita.response.ProjectsItem
import com.capstone.idekita.ui.PmDetailSide.PmDetailProjectActivity
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class ChatRoomAdapter(private val thisAccountUsername : String, options: FirebaseRecyclerOptions<Message>) :

FirebaseRecyclerAdapter<Message, ChatRoomAdapter.MyViewHolder>(options)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvChatbubbleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding,thisAccountUsername)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int,model : Message) {
        holder.bind(model)
    }

    class MyViewHolder(private val binding: RvChatbubbleBinding,private val accountUsername: String) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Message) {
           if(data.name == accountUsername){
               binding.conChatSender.visibility = View.GONE
               binding.tvNameSender.visibility = View.GONE
               binding.conChatMine.visibility = View.VISIBLE
               binding.tvNameMine.visibility = View.VISIBLE
               binding.tvNameMine.text = "${data.name} (PM)"
               binding.tvChatMine.text = data.text
           }else{
               binding.conChatMine.visibility = View.GONE
               binding.tvNameMine.visibility = View.GONE
               binding.conChatSender.visibility = View.VISIBLE
               binding.tvNameSender.visibility = View.VISIBLE
               binding.tvNameSender.text = data.name
               binding.tvChatSender.text = data.text
           }


        }

    }


}

