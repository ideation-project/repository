package com.gemastik.ideation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class postingan(
    val nama:String,
    val deskripsi:String,
    val photo:Int
):Parcelable