package com.gemastik.ideation.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("status")
    val status: String
)

