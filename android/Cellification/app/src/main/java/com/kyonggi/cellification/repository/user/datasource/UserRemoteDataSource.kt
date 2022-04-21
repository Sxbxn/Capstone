package com.kyonggi.cellification.repository.user.datasource

import com.kyonggi.cellification.data.model.user.ResponseUser
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.data.model.user.UserLogin
import retrofit2.Response


interface UserRemoteDataSource {
    suspend fun signInUser(user: User): Response<ResponseUser>
    suspend fun logInUser(login: UserLogin): Response<Void>
    suspend fun withdrawalUser(userId: String): Response<Void>
}