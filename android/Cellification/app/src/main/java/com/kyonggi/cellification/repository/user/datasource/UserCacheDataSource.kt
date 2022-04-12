package com.kyonggi.cellification.repository.user.datasource

import com.kyonggi.cellification.data.model.user.User

interface UserCacheDataSource {
    suspend fun getUsersFromCache(): List<User>
    suspend fun saveUsersToCache(users: List<User>)
}