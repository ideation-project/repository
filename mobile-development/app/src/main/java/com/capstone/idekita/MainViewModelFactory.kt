package com.capstone.idekita

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.idekita.data.ProjectRepository
import com.capstone.idekita.di.Injection
import com.capstone.idekita.ui.home.HomeViewModel

class MainViewModelFactory(private val project: ProjectRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(project) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(project) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MainViewModelFactory? = null

        fun getInstance(context: Context): MainViewModelFactory {
            return INSTANCE
                ?: synchronized(this) {
                    MainViewModelFactory(
                        Injection.provideRepository(context)
                    )
                }
        }
    }

}