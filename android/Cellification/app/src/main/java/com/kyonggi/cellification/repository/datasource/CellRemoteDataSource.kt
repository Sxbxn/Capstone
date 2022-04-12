package com.kyonggi.cellification.repository.datasource

import com.kyonggi.cellification.data.model.cell.Cell
import retrofit2.Response

interface CellRemoteDataSource {
    suspend fun getAllCells(): Response<List<Cell>>
    suspend fun getCell(cellid: Int): Response<Cell>
    suspend fun deleteCell(cellid: Int): Response<Unit>
}