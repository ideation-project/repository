package com.gemastik.ideation.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetAllProjectResponse(

    @field:SerializedName("projects")
    val projects: List<ProjectsItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

@Parcelize
data class ProjectsItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("creator")
    val creator: String,

    @field:SerializedName("nm_proyek")
    val nmProyek: String,

    @field:SerializedName("id_kategori")
    val idKategori: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("tanggal_mulai")
    val tanggalMulai: String,

    @field:SerializedName("tanggal_selesai")
    val tanggalSelesai: String,

    @field:SerializedName("category")
    val category: Category,

    @field:SerializedName("postedAt")
    val postedAt: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("total_rate")
    val totalRate: Int,

    @field:SerializedName("jumlah_raters")
    val jumlahRaters: Int,

    @field:SerializedName("mean_rate")
    val meanRate: Double,



    ):Parcelable

@Parcelize
data class Category(

    @field:SerializedName("nm_kategori")
    val nmKategori: String

):Parcelable

