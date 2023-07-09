package com.capstone.idekita.ui.chatroom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.idekita.data.ProjectRepository
import com.capstone.idekita.di.Injection
import com.capstone.idekita.ui.myProject.MyProjectViewModel



class ChatRoomFactory(private val project: ProjectRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ChatRoomViewModel::class.java) -> {
                ChatRoomViewModel(project) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ChatRoomFactory? = null

        fun getInstance(context: Context): ChatRoomFactory {
            return INSTANCE
                ?: synchronized(this) {
                    ChatRoomFactory(
                        Injection.provideRepository(context)
                    )
                }
        }
    }

}