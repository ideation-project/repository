package com.gemastik.ideation.response

import com.google.gson.annotations.SerializedName

data class ProfilResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: DUser,

    @field:SerializedName("status")
    val status: String
)

data class DUser(

    @field:SerializedName("last_login")
    val lastLogin: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)
