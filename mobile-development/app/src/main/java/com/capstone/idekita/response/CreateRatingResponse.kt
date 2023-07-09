package com.capstone.idekita.response

import com.google.gson.annotations.SerializedName

data class CreateRatingResponse(

	@field:SerializedName("rating")
	val rating: Rating,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Rating(

	@field:SerializedName("id_proyek")
	val id_proyek: Int,

	@field:SerializedName("nilai")
	val nilai: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("username")
	val username: String
)
