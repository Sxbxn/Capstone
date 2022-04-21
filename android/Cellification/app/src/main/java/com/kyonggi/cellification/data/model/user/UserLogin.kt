package com.kyonggi.cellification.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class UserLogin(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)
