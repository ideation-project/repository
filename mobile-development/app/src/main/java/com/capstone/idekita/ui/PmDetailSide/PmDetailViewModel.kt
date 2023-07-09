package com.capstone.idekita.ui.PmDetailSide

import androidx.lifecycle.ViewModel
import com.capstone.idekita.data.ProjectRepository

class PmDetailViewModel(private val projectRepository: ProjectRepository) : ViewModel() {
    fun getProjById(token : String, idProj : Int) = projectRepository.getProjById(token,idProj)
    fun getToken()=projectRepository.getUser()

    fun setThisProjFinish(token : String,id : Int,status : String) = projectRepository.changeStatusProject(token,id,status)

    fun setRating(token : String, idProj : Int, nilai:Int)=projectRepository.setRating(token,idProj,nilai)

    fun getUserRating(token : String) = projectRepository.getUserRating(token)
    fun regisKon(token: String,idProj: Int) = projectRepository.regisKon(token,idProj)

    fun getContributor(token: String,Id: Int?) = projectRepository.getContributorAcc(token,Id)

    fun getWaiting(token : String,id: Int)=projectRepository.getContributorWaiting(token,id)

}