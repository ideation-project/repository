package com.gemastik.ideation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemastik.ideation.data.ProjectRepository
import kotlinx.coroutines.launch

class MainViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    fun getUser() = projectRepository.getUser()

    fun logout() {
        viewModelScope.launch {
            projectRepository.logout()
        }
    }

}