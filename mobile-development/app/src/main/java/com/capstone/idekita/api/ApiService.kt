package com.capstone.idekita.api

import com.capstone.idekita.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
        @Field("pref_categories") PrefCategori:String
    ): Call<RegisterResponse>

    @GET("proyek")
    fun getProjectList(
        @Header("Authorization") token: String,
    ): Call<GetAllProjectResponse>


    @GET("proyek")
    suspend fun getProjectListPaging(
        @Header("Authorization") token: String,
        @Query("Page") page: Int?,
        @Query("size") size: Int?,
        @Query("category") nmKategori: String
    ): Response<GetAllProjectResponse>

    @GET("proyek/kategori/{kategori}")
    suspend fun getProjectByCategori(
        @Header("Authorization") token: String,
        @Path("kategori") kategori:String,
        @Query("Page") page: Int?,
        @Query("size") size: Int?,
    ):Response<GetAllProjectResponse>

    @GET("kategori")
    fun getCategori(
        @Header("Authorization") token: String,
    ):Call<ListKategoriResponse>

    @GET("proyek/cari/{nm_proyek}")
    suspend fun getProjectbyName(
        @Header("Authorization") token: String,
        @Path("nm_proyek") nama:String,
        @Query("Page") page: Int?,
        @Query("size") size: Int?,
    ):GetAllProjectResponse

    @GET("proyek/cari/{nm_proyek}")
    suspend fun getProjectbyNames(
        @Header("Authorization") token: String,
        @Path("nm_proyek") nama:String,
    ):GetAllProjectResponse

    @GET("kontributor/{id}")
    suspend fun getContributor(
        @Header("Authorization") token: String,
        @Path("id") id: Int?,
    ): GetContributorProjectResponse

    @GET("kontributor/menunggu/{id}")
    suspend fun getContributorWaiting(
        @Header("Authorization") token: String,
        @Path("id") id: Int?,
    ): WaitListKontributorResponse

    @FormUrlEncoded
    @POST("kontributor")
    fun postKontributor(
        @Header("Authorization") token: String,
        @Field("id_proyek") id: Int,
        //@Field("username") username: String
    ): Call<RegisContributorResponse>

    @Multipart
    @POST("proyek")
    suspend fun postAddProject(
        @Header("Authorization") token: String,
        @Part("nm_proyek") projName: RequestBody,
        @Part("id_kategori") categoryId: RequestBody,
        @Part("deskripsi") desc: RequestBody,
        @Part("tanggal_mulai") dateStart: RequestBody,
        @Part("tanggal_selesai") dateEnd: RequestBody,
        @Part file: MultipartBody.Part,
    ): CreateProjectResponse

    @GET("proyek/status/{status}")
    suspend fun myProject(
        @Header("Authorization") token: String,
        @Path("status") status: String,
    ): MyProjectResponse

    @GET("profil/{username}")
    suspend fun profil(
        @Header("Authorization") token: String,
        @Path("username") id: String,
    ): ProfilResponse

    @GET("rekomendasi")
    suspend fun getRecomendation(
        @Header("Authorization") token: String,
    ):GetRecomendationResponse

    @GET("rekomendasi")
    fun getRecomendations(
        @Header("Authorization") token: String,
    ):Call<GetRecomendationResponse>

    @Multipart
    @PUT("proyek/{id}")
    suspend fun changeStatus(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("status") status: RequestBody,
    ): ChangeStatusResponse

    @FormUrlEncoded
    @POST("kontributor")
    suspend fun regisKontributor(
        @Header("Authorization") token: String,
        @Field("id_proyek") id_proyek: Int
    ): RegisContributorResponse

    @FormUrlEncoded
    @PUT("kontributor/{id_kontributor}")
    suspend fun reqKontributor(
        @Header("Authorization") token: String,
        @Path("id_kontributor") id_kontributor: Int,
        @Field("status_lamaran") status_lamaran: String,
        @Field("role") role: String,
    ): UpdateContributorResponse

    @GET("proyek/{id}")
    suspend fun getProjByid(
        @Header("Authorization") token: String,
        @Path("id")id : Int,
    ):GetAllProjectResponse

    // tes conflict

    @GET("proyek/{id}")
    suspend fun getProjByid2(
        @Header("Authorization") token: String,
        @Path("id")id : Int,
    ):GetAllProjectResponse

    @GET("proyek/{id}")
    suspend fun getProjByid3(
        @Header("Authorization") token: String,
        @Path("id")id : Int,
    ):GetAllProjectResponse

    @GET("proyek/{id}")
    suspend fun getProjByid4(
        @Header("Authorization") token: String,
        @Path("id")id : Int,
    ):GetAllProjectResponse

    @GET("proyek/{id}")
    suspend fun getProjByidDzul(
        @Header("Authorization") token: String,
        @Path("id")id : Int,
    ):GetAllProjectResponse

    @GET("proyek/{id}")
    suspend fun getProjByidFromDzul(
        @Header("Authorization") token: String,
        @Path("id")id : Int,
    ):GetAllProjectResponse

    // perratingan

    @GET("rating")
    suspend fun getUserRating(
        @Header("Authorization") token: String,
    ):GetUserRatingResponse

    @FormUrlEncoded
    @POST("rating")
    suspend fun setRating(
        @Header("Authorization") token: String,
        @Field("id_proyek") idProj: Int,
        @Field("nilai") role: Int,
    ) : CreateRatingResponse

    // chat room
    @GET("obrolan/{id_proyek}")
    suspend fun getChatRoom(
        @Header("Authorization") token: String,
        @Path("id_proyek") idProj: Int,
    ) : ChatRoomResponse

    @FormUrlEncoded
    @POST("obrolan/{id_proyek}")
    suspend fun sendChat(
        @Header("Authorization") token: String,
        @Path("id_proyek") idProj: Int,
        @Field("message") pesan : String
    ) : SendChatResponse
}