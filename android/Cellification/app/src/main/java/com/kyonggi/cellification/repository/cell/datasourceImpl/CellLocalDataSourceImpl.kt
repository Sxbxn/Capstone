package com.kyonggi.cellification.repository.cell.datasourceImpl

import com.kyonggi.cellification.data.local.dao.CellDao
import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.repository.cell.datasource.CellLocalDataSource

class CellLocalDataSourceImpl(
    private val cellDao : CellDao
): CellLocalDataSource {
    override suspend fun getAllCells(): List<Cell> {
        return cellDao.getAll()
    }

    override suspend fun getCellsFromEmail(email: String): List<Cell> {
        return cellDao.getCellsQueryEmail(email)
    }

    override suspend fun deleteAllCell(email: String) {
        return cellDao.deleteAllCell(email)
    }

    override suspend fun deleteCell(cell: Cell) {
        return cellDao.deleteCell(cell)
    }

    override suspend fun insertCell(cell: Cell) {
        return cellDao.insertCell(cell)
    }
}