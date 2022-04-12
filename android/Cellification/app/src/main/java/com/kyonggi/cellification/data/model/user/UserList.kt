package com.kyonggi.cellification.data.model.user

import com.google.gson.annotations.SerializedName

data class UserList(
    @SerializedName("results")
    val users: List<User>
)
