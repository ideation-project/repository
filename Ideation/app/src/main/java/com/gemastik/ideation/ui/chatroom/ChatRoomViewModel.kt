package com.gemastik.ideation.ui.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gemastik.ideation.data.ProjectRepository
import com.gemastik.ideation.response.MessagesItem


class ChatRoomViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    private val _getListChat = MutableLiveData<List<MessagesItem>>()
    val getListChat : LiveData<List<MessagesItem>> = _getListChat

    fun getChatRoom(token: String,idProj : Int) =
        projectRepository.getChatRoom(token,idProj)

    fun sendChat(token : String, idProj: Int,pesan : String) =projectRepository.sendChat(token,idProj,pesan)


    fun getToken() = projectRepository.getUser()

}