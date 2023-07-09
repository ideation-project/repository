package com.capstone.idekita.result


sealed class TheResult<out R> private constructor() {
    data class Success<out T>(val data: T) : TheResult<T>()
    data class Error(val error: String) : TheResult<Nothing>()
    object Loading : TheResult<Nothing>()
}