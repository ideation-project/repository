package com.gemastik.ideation.ui.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gemastik.ideation.api.ApiConfig
import com.gemastik.ideation.data.ProjectRepository
import com.gemastik.ideation.model.postingan
import com.gemastik.ideation.model.wisataEntity
import com.gemastik.ideation.response.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val projectRepository: ProjectRepository) : ViewModel(){


    fun getUser() = projectRepository.getUser()

    fun getPostingan() = projectRepository.getPostingan()

    private val _listPostingan = MutableLiveData<ArrayList<postingan>>()
    val listPostingan: LiveData<ArrayList<postingan>> = _listPostingan



    fun logout() {
        viewModelScope.launch {
            projectRepository.logout()
        }
    }


    fun getRecomendation(token: String) = projectRepository.getProjectRekomen(token)

    val _listProjectRecomendation =  MutableLiveData<List<RecommendationsItem>>()
    val listProjectRekomen: LiveData<List<RecommendationsItem>> =_listProjectRecomendation


    private val resultRekomen = MediatorLiveData<Result<List<RecommendationsItem>>>()
    fun getAllProjectRekomen(token: String): LiveData<Result<List<RecommendationsItem>>> {

        val client = ApiConfig.getApiService().getRecomendations(token)
        client.enqueue(object : Callback<GetRecomendationResponse> {
            override fun onResponse(
                call: Call<GetRecomendationResponse>,
                response: Response<GetRecomendationResponse>
            ) {
                if (response.isSuccessful) {
                    _listProjectRecomendation.value = response.body()?.recommendations
                }
                Log.e(TAG, response.message())

            }

            override fun onFailure(call: Call<GetRecomendationResponse>, t: Throwable) {

                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
        return resultRekomen
    }


    fun AccProject(token: String, id: Int, statLamaran: String, role: String) =
        projectRepository.reqKon(token,id,statLamaran,role)

    fun getAllProjectPaging(token: String,kategori:String): LiveData<PagingData<ProjectsItem>> {
        return projectRepository.getStoryPaging(token,kategori).cachedIn(viewModelScope)
    }

    fun getSearchProject(token: String,nama:String) = projectRepository.getProjectByName(token,nama)



    //listProject
    private val _listProject = MutableLiveData<List<ProjectsItem>>()
    val listProject: LiveData<List<ProjectsItem>> = _listProject

    private val result = MediatorLiveData<Result<List<ProjectsItem>>>()
    fun getAllProject(token: String): LiveData<Result<List<ProjectsItem>>> {

        val client = ApiConfig.getApiService().getProjectList(token)
        client.enqueue(object : Callback<GetAllProjectResponse> {
            override fun onResponse(
                call: Call<GetAllProjectResponse>,
                response: Response<GetAllProjectResponse>
            ) {
                if (response.isSuccessful) {
                    _listProject.value = response.body()?.projects
                }
                Log.e(ContentValues.TAG, response.message())

            }

            override fun onFailure(call: Call<GetAllProjectResponse>, t: Throwable) {

                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
        return result
    }

    fun getContributorAcc(token: String,Id: Int?) = projectRepository.getContributorAcc(token,Id)

    fun getWaitingContributor(token: String,Id:Int) = projectRepository.getContributorWaiting(token,Id)


    //listKategori
    private val _listCategori = MutableLiveData<List<CategoriesItem>>()
    val listCategori: LiveData<List<CategoriesItem>> = _listCategori

    private val result3 = MediatorLiveData<Result<List<CategoriesItem>>>()
    fun getAllCategori(token: String): LiveData<Result<List<CategoriesItem>>> {

        val client = ApiConfig.getApiService().getCategori(token)
        client.enqueue(object : Callback<ListKategoriResponse> {
            override fun onResponse(
                call: Call<ListKategoriResponse>,
                response: Response<ListKategoriResponse>
            ) {
                if (response.isSuccessful) {
                    _listCategori.value = response.body()?.categories
                }
                Log.e(ContentValues.TAG, response.message())

            }

            override fun onFailure(call: Call<ListKategoriResponse>, t: Throwable) {

                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
        return result3
    }


    private val _listContributorWait = MutableLiveData<List<ContributorsItemWait>>()
    val listContributorWait: LiveData<List<ContributorsItemWait>> = _listContributorWait

    private val result4 = MediatorLiveData<Result<List<ContributorsItemWait>>>()
    val _result = result4
//    fun getAllContributorWait(token: String,id: Int?): LiveData<Result<List<ContributorsItemWait>>> {
//
//        val client = ApiConfig.getApiService().getContributorWaiting(token,id)
//        client.enqueue(object : Callback<WaitListKontributorResponse> {
//            override fun onResponse(
//                call: Call<WaitListKontributorResponse>,
//                response: Response<WaitListKontributorResponse>
//            ) {
//                if (response.isSuccessful) {
//                    _listContributorWait.value= response.body()?.contributorsWait
//                }
//                Log.e(ContentValues.TAG, response.message())
//
//            }
//
//            override fun onFailure(call: Call<WaitListKontributorResponse>, t: Throwable) {
//
//                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
//            }
//        })
//        return result4
//    }

}