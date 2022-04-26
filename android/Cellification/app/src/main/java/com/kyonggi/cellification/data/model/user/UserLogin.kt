package com.kyonggi.cellification.data.model.user

import com.google.gson.annotations.SerializedName

data class UserLogin(
    @SerializedName("email")
    val email: String,
    @SerializedName("pwd")
    val password: String
)
