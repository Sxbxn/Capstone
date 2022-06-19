package com.kyonggi.cellification.data.model.cell

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cell_list")
data class Cell(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val liveCell: Int,
    val deadCell: Int,
    val imageUrl: String,
    val viability: Double,
    val userId: String,
)
