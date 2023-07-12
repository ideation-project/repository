package com.gemastik.ideation.dummy.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class DummyDataStore private constructor(private val dataStore: DataStore<Preferences>) {

    private val TOKEN_KEY = booleanPreferencesKey("dummy_auth")

    fun getToken(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: false
        }
    }

    suspend fun saveToken(theToken: Boolean) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = theToken
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DummyDataStore? = null

        fun getInstance(dataStore: DataStore<Preferences>): DummyDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = DummyDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}