package com.kyonggi.cellification.data.model.cell

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ResponseSpecificUserCell(
    @SerializedName("cellId")
    val cellId: String,
    @SerializedName("totalCell")
    val totalCell: Int,
    @SerializedName("liveCell")
    val liveCell: Int,
    @SerializedName("deadCell")
    val deadCell: Int,
    @SerializedName("viability")
    val viability: Double,
    @SerializedName("createAt")
    val createAt: Date,
    @SerializedName("userid")
    val userId: String
)
