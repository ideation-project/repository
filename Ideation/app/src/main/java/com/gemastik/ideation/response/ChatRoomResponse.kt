package com.gemastik.ideation.response

import com.google.gson.annotations.SerializedName

data class ChatRoomResponse(

	@field:SerializedName("messages")
	val messages: List<MessagesItem>,

	@field:SerializedName("status")
	val status: String
)

data class MessagesItem(

	@field:SerializedName("id_proyek")
	val idProyek: String,

	@field:SerializedName("messageId")
	val messageId: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("username")
	val username: String
)
