package com.kyonggi.cellification.data.model.cell

import com.google.gson.annotations.SerializedName
import java.util.Date

//로그인 했을 시 사용
data class ResponseSpecificUserCell(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("cellId")
    val cellId: String,
    @SerializedName("createAt")
    val createAt: Date,
    @SerializedName("deadCell")
    val deadCell: Int,
    @SerializedName("liveCell")
    val liveCell: Int,
    @SerializedName("viability")
    val viability: Double,
    @SerializedName("userid")
    val userId: String
)
