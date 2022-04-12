package com.kyonggi.cellification.repository.user.datasourceImpl

import com.kyonggi.cellification.data.local.dao.userDao
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.repository.user.datasource.UserLocalDataSource

class UserLocalDataSourceImpl(private val userDao: userDao)
    :UserLocalDataSource{
    override suspend fun getUsersFromDB(): List<User> {
        return userDao.getUsers()
    }

    override suspend fun saveUsersToDB(users: List<User>) {
        return userDao.saveUsers(users)
    }

    override suspend fun clearAllUsers() {
        userDao.deleteUsers()
    }
}