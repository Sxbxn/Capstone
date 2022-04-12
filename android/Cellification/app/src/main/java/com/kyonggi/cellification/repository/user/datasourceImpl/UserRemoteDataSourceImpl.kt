package com.kyonggi.cellification.repository.user.datasourceImpl

import com.kyonggi.cellification.data.model.user.UserList
import com.kyonggi.cellification.repository.user.datasource.UserRemoteDataSource
import retrofit2.Response

class UserRemoteDataSourceImpl(
    //private val cellService: CellService,
    private val apiKey: String
):UserRemoteDataSource {
    /*override suspend fun getUsers(): Response<UserList> {
        return cellService.getAllUsers(apiKey)
    }*/
}