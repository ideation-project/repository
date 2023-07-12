package com.gemastik.ideation.dummy.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gemastik.ideation.dummy.data.DummyDataStore
import kotlinx.coroutines.launch


class DummyViewModel(private val pref: DummyDataStore) : ViewModel() {
    fun getToken(): LiveData<Boolean> {
        return pref.getToken().asLiveData()
    }

    fun saveToken(token: Boolean) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }
}

