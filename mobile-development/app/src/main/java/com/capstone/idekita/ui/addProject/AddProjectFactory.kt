package com.capstone.idekita.ui.addProject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.idekita.data.ProjectRepository
import com.capstone.idekita.di.Injection

class AddProjectFactory(private val repository: ProjectRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddProjectViewModel::class.java) -> {
                AddProjectViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AddProjectFactory? = null

        fun getInstance(context: Context): AddProjectFactory {
            return INSTANCE
                ?: synchronized(this) {
                    AddProjectFactory(
                        Injection.provideRepository(context)
                    )
                }
        }
    }

}