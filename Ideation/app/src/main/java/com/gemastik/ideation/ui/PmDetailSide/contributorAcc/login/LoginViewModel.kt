package com.gemastik.ideation.ui.PmDetailSide.contributorAcc.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemastik.ideation.UserPreference
import com.gemastik.ideation.model.UserModel
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