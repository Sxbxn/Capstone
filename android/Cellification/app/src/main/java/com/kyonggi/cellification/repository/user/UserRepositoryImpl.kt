package com.kyonggi.cellification.repository.user

import com.kyonggi.cellification.data.model.user.ResponseUser
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.repository.user.datasource.UserRemoteDataSource
import com.kyonggi.cellification.utils.APIResponse
import okhttp3.Headers

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun signInUser(user: User): APIResponse<ResponseUser> {
        val response = userRemoteDataSource.signInUser(user)
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return APIResponse.Success(result)
            }
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun getAccessToken(login: UserLogin): APIResponse<Headers> {
        val response = userRemoteDataSource.logInUser(login)
        if (response.isSuccessful) {
            response.headers().let { result ->
                return APIResponse.Success(result)
            }
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun withdrawalUSer(token: String, userId: String): APIResponse<Void> {
        val response = userRemoteDataSource.withdrawalUser(token, userId)
        if (response.isSuccessful) {
            return APIResponse.Success()
        }
        return APIResponse.Error(response.message())
    }
}