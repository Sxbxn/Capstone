package com.kyonggi.cellification.data.model.cell

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cell_list")
data class Cell(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val livingCell: Int,
    val dieCell: Int,
    val imageUrl: String,
    val livingCellPercent: Double,
    val dieCellPercent: Double,
    val email: String,
)
