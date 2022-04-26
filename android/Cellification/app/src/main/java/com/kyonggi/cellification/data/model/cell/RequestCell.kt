package com.kyonggi.cellification.data.model.cell

import com.google.gson.annotations.SerializedName

data class RequestCell(
    @SerializedName("totalCell")
    val totalCell: Int,
    @SerializedName("liveCell")
    val liveCell: Int,
    @SerializedName("deadCell")
    val deadCell: Int
)
