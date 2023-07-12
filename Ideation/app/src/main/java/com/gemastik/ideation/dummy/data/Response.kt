package com.gemastik.ideation.dummy.data

import android.os.Parcelable
import com.gemastik.ideation.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(
    val name: String,
    val photo: Int,
    val desc: String,
    val date: String
) : Parcelable

@Parcelize
data class MyProject(
    val name: String,
    val pmName: String,
    val projImg: Int,
    val pmImg: Int,
    val category: String,
    val status: String,
) : Parcelable

object DummyList {
    fun getTheList(): List<Response> {
        val theList = ArrayList<Response>()
        for (i in 0..10) {
            val list = Response(
                "Adi suhardi",
                R.drawable.logo_ide_kita_ik,
                "ini hanyalah dummy data",
                "12/12/12"
            )
            theList.add(list)
        }
        return theList
    }
}

object DummyListHorizotal {
    fun getTheList(): List<Response> {
        val theList = ArrayList<Response>()
        for (i in 0..10) {
            val list = Response(
                "Projek saya",
                R.drawable.page_2_gabut_idekita,
                "ini hanyalah dummy data",
                "12/12/12"


            )
            theList.add(list)
        }
        return theList
    }
}

object Ongoing {
    fun getTheList(): List<MyProject> {
        val theList = ArrayList<MyProject>()
        for (i in 0..5) {
            val list = MyProject(
                "Projek saya",
                "Project Manager",
                R.drawable.page_2_gabut_idekita,
                R.drawable.holder_person,
                "Category : Some Category",
                "Sedang Berlangsung"
            )
            theList.add(list)
        }
        return theList
    }

    fun getTheDone(): List<MyProject> {
        val theList = ArrayList<MyProject>()
        for (i in 0..1) {
            val list = MyProject(
                "Projek saya",
                "Project Manager",
                R.drawable.page_2_gabut_idekita,
                R.drawable.holder_person,
                "Category : Some Category",
                "Selesai"
            )
            theList.add(list)
        }
        return theList
    }
}