package com.gemastik.ideation.response

import com.google.gson.annotations.SerializedName

data class RegisContributorResponse(

    @field:SerializedName("contributor")
    val contributor: Contributor,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class Contributor(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("id_proyek")
    val idProyek: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("status_lamaran")
    val statusLamaran: String,

    @field:SerializedName("username")
    val username: String
)
