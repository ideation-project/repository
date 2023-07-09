package com.capstone.idekita.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val gmail: String,
    val token: String,
   // val id : String,
    val isLogin: Boolean
) : Parcelable