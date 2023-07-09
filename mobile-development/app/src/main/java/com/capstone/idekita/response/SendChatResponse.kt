package com.capstone.idekita.response

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

data class SendChatResponse(

	@field:SerializedName("chatId")
	val chatId: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

@IgnoreExtraProperties
data class Message(
	val text: String? = null,
	val name: String? = null,
	val timestamp: Long? = null
){
	// Null default values create a no-argument default constructor, which is needed
	// for deserialization from a DataSnapshot.
}
