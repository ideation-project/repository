package com.capstone.idekita.response

import com.google.gson.annotations.SerializedName

data class GetContributorProjectResponse(

    @field:SerializedName("contributors")
    val contributors: List<ContributorsItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class ContributorsItem(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("username")
    val username: String
)
