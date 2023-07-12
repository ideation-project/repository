package com.gemastik.ideation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.gemastik.ideation.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {


    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME_KEY] ?: "",
                preferences[GMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                //preferences[ID_KEY]?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[GMAIL_KEY] = user.gmail
            preferences[TOKEN_KEY] = user.token
            //preferences[ID_KEY] = user.id
            preferences[STATE_KEY] = user.isLogin
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
            preferences.remove(STATE_KEY)
            preferences.remove(TOKEN_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val GMAIL_KEY = stringPreferencesKey("gmail")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ID_KEY = stringPreferencesKey("id")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}