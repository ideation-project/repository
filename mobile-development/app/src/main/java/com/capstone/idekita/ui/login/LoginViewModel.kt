package com.capstone.idekita.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.idekita.UserPreference
import com.capstone.idekita.model.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

}