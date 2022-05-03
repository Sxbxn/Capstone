package com.kyonggi.cellification.data.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pwd")
    val pwd: String
)