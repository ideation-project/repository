package com.capstone.idekita.response

import com.google.gson.annotations.SerializedName

data class MyProjectResponse(

    @field:SerializedName("projects")
    val projects: List<DProjectsItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)



data class DContributorsItem(

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

data class DProjectsItem(

    @field:SerializedName("mean_rate")
    val meanRate: Double,

    @field:SerializedName("creator")
    val creator: String,

    @field:SerializedName("nm_proyek")
    val nmProyek: String,

    @field:SerializedName("id_kategori")
    val idKategori: Int,

    @field:SerializedName("total_rate")
    val totalRate: Int,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("tanggal_selesai")
    val tanggalSelesai: String,

    @field:SerializedName("jumlah_raters")
    val jumlahRaters: Int,

    @field:SerializedName("tanggal_mulai")
    val tanggalMulai: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("contributors")
    val contributors: List<DContributorsItem>,

    @field:SerializedName("category")
    val category: Category,

    @field:SerializedName("status")
    val status: String
)
