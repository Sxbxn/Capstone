package com.kyonggi.cellification.repository.user.datasourceImpl

import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.repository.user.datasource.UserCacheDataSource

class UserCacheDataSourceImpl:UserCacheDataSource {
    private val userList = ArrayList<User>()
    override suspend fun getUsersFromCache(): List<User> {
        return userList
    }

    override suspend fun saveUsersToCache(users: List<User>) {
        userList.clear()
        userList.addAll(userList)
    }
}