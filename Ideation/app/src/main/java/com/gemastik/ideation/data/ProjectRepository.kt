package com.gemastik.ideation.data

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.gemastik.ideation.UserPreference
import com.gemastik.ideation.api.ApiService
import com.gemastik.ideation.model.UserModel
import com.gemastik.ideation.model.postingan
import com.gemastik.ideation.model.postinganData
import com.gemastik.ideation.response.*
import com.gemastik.ideation.result.TheResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException

class ProjectRepository(private val apiService: ApiService, private val pref: UserPreference) {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getPostingan():List<postingan>{
        return postinganData.postingan
    }

    suspend fun logout() {
        pref.logout()
    }

    fun getStoryPaging(token: String,kategori:String): LiveData<PagingData<ProjectsItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                ProjectPagingSource(apiService, "Bearer ${token}",kategori)
            }
        ).liveData
    }

    fun getProjectRekomen(token: String): LiveData<TheResult<List<RecommendationsItem>>> = liveData {
        emit(TheResult.Loading)
        try {
            val response = apiService.getRecomendation("Bearer $token")
            val successPost = TheResult.Success(response.recommendations)
            emit(successPost)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    //get project by name
    fun getProjectByName(token: String,nama:String): LiveData<TheResult<List<ProjectsItem>>> = liveData {
        emit(TheResult.Loading)
        try {
            val response = apiService.getProjectbyNames("Bearer $token", nama)
            val successPost = TheResult.Success(response.projects)
            emit(successPost)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    //Fungsi Contributor Waiting
    fun getContributorWaiting(token: String, Id: Int): LiveData<TheResult<List<ContributorsItemWait>>> = liveData {
        emit(TheResult.Loading)
        try {
            val response = apiService.getContributorWaiting("Bearer $token", Id)
            val successPost = TheResult.Success(response.contributorsWait)
            emit(successPost)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    //Get Contributor Acc
    fun getContributorAcc(token: String, Id: Int?): LiveData<TheResult<List<ContributorsItem>>> = liveData {
        emit(TheResult.Loading)
        try {
            val response = apiService.getContributor("Bearer ${token}", Id)
            val successPost = TheResult.Success(response.contributors)
            emit(successPost)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
            emit(TheResult.Error(errorMassage.toString()))
        }
    }





    // Fungsi add project
    fun postAddProject(
        token: String,
        projName: String,
        categoryId: String,
        deskripsi: String,
        dateStart: String,
        dateFinish: String,
        imgProj: MultipartBody.Part
    ): LiveData<TheResult<CreateProjectResponse>> = liveData {
        emit(TheResult.Loading)
        try {
            val projNameTo = projName.toRequestBody("text/plain".toMediaType())
            val catIdTo = categoryId.toRequestBody("text/plain".toMediaType())
            val deskripsiTo = deskripsi.toRequestBody("text/plain".toMediaType())
            val dateStartTo = dateStart.toRequestBody("text/plain".toMediaType())
            val dateFinishTo = dateFinish.toRequestBody("text/plain".toMediaType())
            val response = apiService.postAddProject(
                "Bearer $token",
                projNameTo,
                catIdTo,
                deskripsiTo,
                dateStartTo,
                dateFinishTo,
                imgProj,
            )
            val successPost = TheResult.Success(response)
            emit(successPost)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    fun profil(token: String, username: String): LiveData<TheResult<ProfilResponse>> = liveData {
        emit(TheResult.Loading)
        try {
            val response = apiService.profil("Bearer $token", username)
            val successPost = TheResult.Success(response)
            emit(successPost)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    fun changeStatusProject(
        token: String,
        id: Int,
        status: String
    ): LiveData<TheResult<ChangeStatusResponse>> = liveData {
        emit(TheResult.Loading)
        try {
            val statusTo = status.toRequestBody("text/plain".toMediaType())
            val response = apiService.changeStatus("Bearer $token", id, statusTo)
            val successPost = TheResult.Success(response)
            emit(successPost)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    fun regisKon(token: String, id_proj: Int): LiveData<TheResult<RegisContributorResponse>> =
        liveData {
            emit(TheResult.Loading)
            try {
                val response = apiService.regisKontributor("Bearer $token", id_proj)
                val successPost = TheResult.Success(response)
                emit(successPost)

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
                emit(TheResult.Error(errorMassage.toString()))
            }
        }

    fun reqKon(
        token: String,
        id: Int,
        statLamaran: String,
        role: String
    ): LiveData<TheResult<UpdateContributorResponse>> = liveData {
        emit(TheResult.Loading)
        try {
            val response = apiService.reqKontributor("Bearer $token", id, statLamaran, role)
            val successPost = TheResult.Success(response)
            emit(successPost)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    fun getMyProjectItem(token: String, status : String): LiveData<TheResult<List<DProjectsItem>>> =
        liveData {
            emit(TheResult.Loading)
            try {
                lateinit var response : List<DProjectsItem>
                if(status == "berlangsung"){
                    response = apiService.myProject("Bearer $token", "berlangsung").projects + apiService.myProject("Bearer $token", "terbuka").projects
                }else{
                    response = apiService.myProject("Bearer $token", "selesai").projects
                }

                val successPost = TheResult.Success(response )
                emit(successPost)

            } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
            }
        }

    fun getProjById(token : String, idProj : Int):LiveData<TheResult<List<ProjectsItem>>> = liveData{
        emit(TheResult.Loading)
        try {
            val response = apiService.getProjByid("Bearer $token", idProj)
            val successPost = TheResult.Success(response.projects)
            emit(successPost)

        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
        }
    }

//    fun getProjectRecomendation(token : String):LiveData<TheResult<List<RecommendationsItem>>> = liveData{
//        emit(TheResult.Loading)
//        try {
//            val response = apiService.getRecomendation("Bearer $token")
//            val successPost = TheResult.Success(response.recommendations)
//            emit(successPost)
//
//        } catch (e: HttpException) {
////            val errorBody = e.response()?.errorBody()?.string()
////            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
////            emit(TheResult.Error(errorMassage.toString()))
//        }
//    }

    //private val _listProjectRecomendation = MutableLiveData<List<RecommendationsItem>>()



    // tes conflict

    fun getProjById1(token : String, idProj : Int):LiveData<TheResult<List<ProjectsItem>>> = liveData{
        emit(TheResult.Loading)
        try {
            val response = apiService.getProjByid("Bearer $token", idProj)
            val successPost = TheResult.Success(response.projects)
            emit(successPost)

        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    fun getProjById2(token : String, idProj : Int):LiveData<TheResult<List<ProjectsItem>>> = liveData{
        emit(TheResult.Loading)
        try {
            val response = apiService.getProjByid("Bearer $token", idProj)
            val successPost = TheResult.Success(response.projects)
            emit(successPost)

        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    fun getProjById3(token : String, idProj : Int):LiveData<TheResult<List<ProjectsItem>>> = liveData{
        emit(TheResult.Loading)
        try {
            val response = apiService.getProjByid("Bearer $token", idProj)
            val successPost = TheResult.Success(response.projects)
            emit(successPost)

        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    // per ratingan

    fun getUserRating(token : String):LiveData<TheResult<List<RatingsItem>>> = liveData{
        emit(TheResult.Loading)
        try {
            val response = apiService.getUserRating("Bearer $token")
            val successPost = TheResult.Success(response.ratings)
            emit(successPost)

        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    fun setRating(token : String,idProj : Int,nilai : Int):LiveData<TheResult<CreateRatingResponse>> = liveData{
        emit(TheResult.Loading)
        try {
            val response = apiService.setRating("Bearer $token",idProj,nilai)
            val successPost = TheResult.Success(response)
            emit(successPost)

        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    // chatroom

    fun getChatRoom(token : String,idProj : Int):LiveData<TheResult<List<MessagesItem>>> = liveData{
        emit(TheResult.Loading)
        try {
            val response = apiService.getChatRoom("Bearer $token",idProj)
            val successPost = TheResult.Success(response.messages)
            emit(successPost)

        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
        }
    }

    fun sendChat(token : String,idProj : Int,pesan : String):LiveData<TheResult<SendChatResponse>> = liveData{
        emit(TheResult.Loading)
        try {
            val response = apiService.sendChat("Bearer $token",idProj,pesan)
            val successPost = TheResult.Success(response)
            emit(successPost)

        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorMassage = errorBody?.let { JSONObject(it).getString("message") }
//            emit(TheResult.Error(errorMassage.toString()))
        }
    }


}