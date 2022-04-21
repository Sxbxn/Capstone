package com.kyonggi.cellification.data.remote.service

import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.data.model.user.UserRegister
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface UserService {

    @POST("/capstone-user-service/users")
    suspend fun registUser(
        @Body userRegister: UserRegister
    ): Response<UserRegister>

    @POST("/capstone-user-service/login")
    suspend fun loginUser(
        @Body userLogin: UserLogin,
    ): Response<UserLogin>

    @GET("/capstone-user-service/users")
    suspend fun getUser(
        @Body userRegister: UserRegister
    ): Response<List<UserRegister>>

    @GET("capstone-user-service/users/{userId}")
    suspend fun getOneUser(
        @Path("userId") userId: String
    ): Response<UserRegister>

    @DELETE("/capstone-user-service/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: String
    ): Response<Unit>

    //cell 사진을 서버로 보내기
    @Multipart
    @POST("")
    suspend fun sendCellImage(
        @Part cellImage: MultipartBody.Part?
    ) :Response<Unit>
}