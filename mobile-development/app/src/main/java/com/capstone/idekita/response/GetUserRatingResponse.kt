package com.capstone.idekita.response

data class GetUserRatingResponse(
	val ratings: List<RatingsItem>,
	val message: String,
	val status: String
)

data class RatingsItem(
	val createdAt: String,
	val id_proyek: Int,
	val nilai: Int,
	val id: Int,
	val username: String,
	val updatedAt: String
)

