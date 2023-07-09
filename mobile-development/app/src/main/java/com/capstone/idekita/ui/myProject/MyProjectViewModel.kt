package com.capstone.idekita.ui.myProject

import androidx.lifecycle.ViewModel
import com.capstone.idekita.data.ProjectRepository

class MyProjectViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    fun getMyproject(token: String,status : String) =
        projectRepository.getMyProjectItem(token,status)

    fun getToken() = projectRepository.getUser()

}