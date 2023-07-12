package com.gemastik.ideation.ui.PmDetailSide

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gemastik.ideation.data.ProjectRepository
import com.gemastik.ideation.di.Injection

class PmDetailFactory(private val repository: ProjectRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PmDetailViewModel::class.java) -> {
                PmDetailViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PmDetailFactory? = null

        fun getInstance(context: Context): PmDetailFactory {
            return INSTANCE
                ?: synchronized(this) {
                    PmDetailFactory(
                        Injection.provideRepository(context)
                    )
                }
        }
    }

}