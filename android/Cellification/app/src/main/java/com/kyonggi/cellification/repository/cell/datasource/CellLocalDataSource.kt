package com.kyonggi.cellification.repository.cell.datasource

import com.kyonggi.cellification.data.model.cell.Cell

interface CellLocalDataSource {
    suspend fun getAllCells(): List<Cell>
    suspend fun getCellsFromEmail(email: String): List<Cell>
    suspend fun deleteAllLocalCell(email: String)
    suspend fun deleteCell(cell:Cell)
    suspend fun insertCell(cell:Cell)
}