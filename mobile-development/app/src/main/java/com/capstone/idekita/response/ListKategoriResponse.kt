package com.capstone.idekita.response

import com.google.gson.annotations.SerializedName

data class ListKategoriResponse(

    @field:SerializedName("categories")
    val categories: List<CategoriesItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class CategoriesItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nm_kategori")
    val nmKategori: String
)
