package com.gemastik.ideation.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.gemastik.ideation.UserPreference
import com.gemastik.ideation.api.ApiConfig
import com.gemastik.ideation.data.ProjectRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideRepository(context: Context): ProjectRepository {
        val apiService = ApiConfig.getApiService()
        val userPreference = UserPreference.getInstance(context.dataStore)
        return ProjectRepository(apiService, userPreference)
    }

}