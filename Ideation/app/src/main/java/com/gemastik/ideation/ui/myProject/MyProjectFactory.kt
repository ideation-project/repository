package com.gemastik.ideation.ui.myProject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gemastik.ideation.data.ProjectRepository
import com.gemastik.ideation.di.Injection


class MyProjectFactory(private val project: ProjectRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MyProjectViewModel::class.java) -> {
                MyProjectViewModel(project) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MyProjectFactory? = null

        fun getInstance(context: Context): MyProjectFactory {
            return INSTANCE
                ?: synchronized(this) {
                    MyProjectFactory(
                        Injection.provideRepository(context)
                    )
                }
        }
    }

}