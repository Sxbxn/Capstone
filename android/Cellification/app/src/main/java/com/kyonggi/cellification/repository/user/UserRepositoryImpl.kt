package com.kyonggi.cellification.repository.user

import android.util.Log
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.repository.user.datasource.UserCacheDataSource
import com.kyonggi.cellification.repository.user.datasource.UserLocalDataSource
import com.kyonggi.cellification.repository.user.datasource.UserRemoteDataSource
import java.lang.Exception

class UserRepositoryImpl(
    private val userCacheDataSource: UserCacheDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun getUser(): List<User> {
        return getUsersFromCache()
    }

    override suspend fun updateUser(): List<User> {
        val newUserList = getUsersFromAPI()
        userLocalDataSource.clearAllUsers()
        userLocalDataSource.saveUsersToDB(newUserList)
        userCacheDataSource.saveUsersToCache(newUserList)
        return newUserList
    }
    suspend fun getUsersFromCache(): List<User> {
        lateinit var userList: List<User>
        try{
            userList = userCacheDataSource.getUsersFromCache()
        }catch (e:Exception){
            Log.e("TAG",e.message.toString())
        }
        if(userList.isNotEmpty()){
            return userList
        } else{
            userList = getUsersFromDB()
            userCacheDataSource.saveUsersToCache(userList)
        }
        return userList
    }

    suspend fun getUsersFromDB(): List<User> {
        lateinit var userList: List<User>
        try{
            userList = userLocalDataSource.getUsersFromDB()
        }catch (e:Exception){
            Log.e("TAG",e.message.toString())
        }
        if(userList.isNotEmpty()){
            return userList
        } else{
            userList = getUsersFromAPI()
            userLocalDataSource.saveUsersToDB(userList)
        }
        return userList
    }

    suspend fun getUsersFromAPI(): List<User> {
        lateinit var userList: List<User>
        try{
            //val reponse = userRemoteDataSource.getUsers()
            //val body = reponse.body()
            //if(body != null)
            //    userList = body.users
        }catch(e:Exception){
            Log.e("TAG",e.message.toString())
        }
        return userList
    }
}