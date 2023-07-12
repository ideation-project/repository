package com.gemastik.ideation.response

import com.google.gson.annotations.SerializedName

data class ChangeStatusResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)
