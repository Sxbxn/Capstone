package com.kyonggi.cellification.repository.cell.datasource

import com.kyonggi.cellification.data.model.cell.Cell
import retrofit2.Response

interface CellRemoteDataSource {
    suspend fun getAllCells(userId: String): Response<List<Cell>>
    suspend fun deleteCell(userId: String, cellId: String): Response<Unit>
}