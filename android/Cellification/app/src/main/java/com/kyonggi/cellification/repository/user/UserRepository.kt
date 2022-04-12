package com.kyonggi.cellification.repository.user

import com.kyonggi.cellification.data.model.user.User

interface UserRepository {
    suspend fun getUser(): List<User>?
    suspend fun updateUser(): List<User>?
}