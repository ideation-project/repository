package com.capstone.idekita.ui.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.idekita.data.ProjectRepository
import com.capstone.idekita.response.MessagesItem
import kotlinx.coroutines.launch


class ChatRoomViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    private val _getListChat = MutableLiveData<List<MessagesItem>>()
    val getListChat : LiveData<List<MessagesItem>> = _getListChat

    fun getChatRoom(token: String,idProj : Int) =
        projectRepository.getChatRoom(token,idProj)

    fun sendChat(token : String, idProj: Int,pesan : String) =projectRepository.sendChat(token,idProj,pesan)


    fun getToken() = projectRepository.getUser()

}