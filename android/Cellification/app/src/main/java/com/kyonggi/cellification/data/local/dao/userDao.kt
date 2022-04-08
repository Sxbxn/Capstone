package com.kyonggi.cellification.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyonggi.cellification.data.model.user.User
@Dao
interface userDao {
    @Query("SELECT * FROM users")
    suspend fun getUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(users: List<User>)

    @Query("DELETE FROM users")
    suspend fun deleteUser(): List<User>
}
