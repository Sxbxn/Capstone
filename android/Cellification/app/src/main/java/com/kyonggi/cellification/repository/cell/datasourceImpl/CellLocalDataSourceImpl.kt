package com.kyonggi.cellification.repository.cell.datasourceImpl

import com.kyonggi.cellification.data.local.dao.CellDao
import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.repository.cell.datasource.CellLocalDataSource

class CellLocalDataSourceImpl(
    private val cellDao : CellDao
): CellLocalDataSource {
    override suspend fun getAllCells(): MutableList<Cell> {
        return cellDao.getAll()
    }

    override suspend fun getCellsFromEmail(email: String): MutableList<Cell> {
        return cellDao.getCellsQueryEmail(email)
    }

    override suspend fun deleteAllLocalCell(email: String) {
        return cellDao.deleteAllLocalCell(email)
    }

    override suspend fun deleteCell(cell: Cell) {
        return cellDao.deleteCell(cell)
    }

    override suspend fun insertCell(cell: Cell) {
        return cellDao.insertCell(cell)
    }
}