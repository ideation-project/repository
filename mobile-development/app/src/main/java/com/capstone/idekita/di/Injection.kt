package com.capstone.idekita.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.idekita.UserPreference
import com.capstone.idekita.api.ApiConfig
import com.capstone.idekita.data.ProjectRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideRepository(context: Context): ProjectRepository {
        val apiService = ApiConfig.getApiService()
        val userPreference = UserPreference.getInstance(context.dataStore)
        return ProjectRepository(apiService, userPreference)
    }

}