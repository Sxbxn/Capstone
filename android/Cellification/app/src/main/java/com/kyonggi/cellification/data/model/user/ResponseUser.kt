package com.kyonggi.cellification.data.model.user

import com.google.gson.annotations.SerializedName
import com.kyonggi.cellification.data.model.cell.ResponseCell

data class ResponseUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("cells")
    val cells: List<ResponseCell>
)
