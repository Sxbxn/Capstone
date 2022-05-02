package com.kyonggi.cellification.repository.user

import com.kyonggi.cellification.data.model.user.ResponseUser
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.utils.APIResponse
import okhttp3.Headers
import okhttp3.MultipartBody
import retrofit2.Response

interface UserRepository {
    suspend fun signInUser(user: User): APIResponse<ResponseUser>
    suspend fun getAccessToken(login: UserLogin): APIResponse<Headers>
    suspend fun withdrawalUSer(userId: String): APIResponse<Void>
    suspend fun sendCellImage(token:String, image: MultipartBody.Part): APIResponse<String>
}