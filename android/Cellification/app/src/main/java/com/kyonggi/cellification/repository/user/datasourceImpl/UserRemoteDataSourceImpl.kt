package com.kyonggi.cellification.repository.user.datasourceImpl

import com.kyonggi.cellification.repository.user.datasource.UserRemoteDataSource

class UserRemoteDataSourceImpl(
    //private val cellService: CellService,
    private val apiKey: String
):UserRemoteDataSource {
    /*override suspend fun getUsers(): Response<UserList> {
        return cellService.getAllUsers(apiKey)
    }*/
}