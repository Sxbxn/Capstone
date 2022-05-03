package com.kyonggi.cellification.repository.user.datasourceImpl

import com.kyonggi.cellification.data.model.user.ResponseUser
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.data.remote.service.UserService
import com.kyonggi.cellification.repository.user.datasource.UserRemoteDataSource
import okhttp3.MultipartBody
import retrofit2.Response

class UserRemoteDataSourceImpl(
    private val userService: UserService
): UserRemoteDataSource {
    override suspend fun signInUser(user: User): Response<ResponseUser> {
        return userService.signInUser(user)
    }

    override suspend fun logInUser(login: UserLogin): Response<Void> {
        return userService.logInUser(login)
    }

    override suspend fun withdrawalUser(token: String, userId: String): Response<Void> {
        return userService.withdrawalUser(token, userId)
    }
    override suspend fun sendCellImage(token:String, image: MultipartBody.Part): Response<String> {
        return userService.sendCellImage(token, image)
    }
}