package com.kyonggi.cellification.data.model.cell

import com.google.gson.annotations.SerializedName

// 로그인 안했을시 사용? 저장은 못하게..
data class ResponseCell(
    @SerializedName("cellId")
    val cellId: String,
    @SerializedName("totalCell")
    val totalCell: Int,
    @SerializedName("liveCell")
    val liveCell: Int,
    @SerializedName("deadCell")
    val deadCell: Int,
    @SerializedName("viability")
    val viability: Double
)
