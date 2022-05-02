package com.kyonggi.cellification.data.remote.service

import com.kyonggi.cellification.data.model.user.ResponseUser
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.data.model.user.UserLogin
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    // 회원가입
    @POST("/capstone-user-service/users")
    suspend fun signInUser(
        @Body user: User
    ): Response<ResponseUser>

    // 로그인
    @POST("/capstone-user-service/login")
    suspend fun logInUser(
        @Body login: UserLogin
    ): Response<Void>

    // 회원탈퇴
    @DELETE("/capstone-user-service/{userId}")
    suspend fun withdrawalUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Response<Void>
}