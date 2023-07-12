package com.gemastik.ideation.ui.myProject

import androidx.lifecycle.ViewModel
import com.gemastik.ideation.data.ProjectRepository

class MyProjectViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    fun getMyproject(token: String,status : String) =
        projectRepository.getMyProjectItem(token,status)

    fun getToken() = projectRepository.getUser()

}