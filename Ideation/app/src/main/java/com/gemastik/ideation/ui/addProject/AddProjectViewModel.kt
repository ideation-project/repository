package com.gemastik.ideation.ui.addProject

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.gemastik.ideation.data.ProjectRepository
import okhttp3.MultipartBody
import java.text.SimpleDateFormat
import java.util.*

class AddProjectViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    fun addProject(
        token: String,
        projName: String,
        idCat: String,
        desc: String,
        dateStart: String,
        dateFinish: String,
        img: MultipartBody.Part
    ) = projectRepository.postAddProject(
        token,
        projName,
        idCat,
        desc,
        dateStart,
        dateFinish,
        img
    )

    // get today date
    @SuppressLint("SimpleDateFormat")
    fun getTodayDate(): String {
        val calendar = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy/MM/dd")

        return formatter.format(calendar)
    }

    fun getProfil(token: String, username: String) = projectRepository.profil(token, username)
    fun regisKon(token: String, id_proj: Int) = projectRepository.regisKon(token, id_proj)
    fun reqKon(token: String, id: Int, statLamaran: String, role: String) =
        projectRepository.reqKon(token, id, statLamaran, role)

    fun changeStatus(token: String, id: Int, status: String) =
        projectRepository.changeStatusProject(token, id, status)

    fun getToken() = projectRepository.getUser()

    fun cekKategori(cat: String): String {
        var ret = ""
        when (cat) {
            "Sosial" -> ret = "1"
            "Pendidikan" -> ret = "2"
            "Kesehatan" -> ret = "3"
            "Budaya" -> ret = "4"
            "Pemerintahan" -> ret = "5"
            "Sains" ->ret = "6"
            "Lingkungan" ->ret = "7"
            "Olahraga" ->ret = "8"
            "Kesejahteraan" ->ret = "9"
            "Hukum" ->ret = "10"
        }
        return ret
    }

}