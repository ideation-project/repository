package com.capstone.idekita.ui.chatroom

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.capstone.idekita.databinding.ActivityAddProjectBinding
import com.capstone.idekita.databinding.ActivityChatBinding
import com.capstone.idekita.response.Message
import com.capstone.idekita.response.ProjectsItem
import com.capstone.idekita.result.TheResult
import com.capstone.idekita.ui.PmDetailSide.PmDetailProjectActivity
import com.capstone.idekita.ui.addProject.AddProjectFactory
import com.capstone.idekita.ui.addProject.AddProjectViewModel
import com.capstone.idekita.ui.myProject.MyProjectAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import okhttp3.internal.notify
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val viewModel by viewModels<ChatRoomViewModel> {
        ChatRoomFactory.getInstance(this)
    }


    private lateinit var adapter: ChatRoomAdapter
    private lateinit var db: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tes fire base
        db = Firebase.database
        val projId = intent.getIntExtra(PROJ_ID,1)
        val messagesRef = db.reference.child(projId.toString())

        binding.btSend.setOnClickListener {
            val username = intent.getStringExtra(USER_NAME)
            val friendlyMessage = Message(
                binding.edChatBox.text.toString(),
                username,
                Date().time
            )
            messagesRef.push().setValue(friendlyMessage) { error, _ ->
                if (error != null) {
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "sukses mengirim chat", Toast.LENGTH_SHORT).show()
                }
            }
            binding.edChatBox.setText("")
        }

        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.rvChat.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, Message::class.java)
            .build()



        val username = intent.getStringExtra(USER_NAME)
           adapter = ChatRoomAdapter(username!!,options)
            binding.rvChat.adapter = adapter



    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }
    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }



    companion object{
        const val PROJ_ID = "proyek_id"
        const val USER_NAME = "user_name"
    }
}