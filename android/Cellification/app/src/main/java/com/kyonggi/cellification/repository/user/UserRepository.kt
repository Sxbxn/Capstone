package com.kyonggi.cellification.repository.user

import com.kyonggi.cellification.data.model.user.ResponseUser
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.utils.APIResponse
import okhttp3.Headers

interface UserRepository {
    suspend fun signInUser(user: User): APIResponse<ResponseUser>
    suspend fun getAccessToken(login: UserLogin): APIResponse<Headers>
    suspend fun withdrawalUSer(userId: String): APIResponse<Void>
}