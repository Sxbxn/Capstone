package com.kyonggi.cellification.repository.cell.datasourceImpl

import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.remote.api.CellServiceRequestFactory
import com.kyonggi.cellification.data.remote.service.CellService
import com.kyonggi.cellification.repository.cell.datasource.CellRemoteDataSource
import retrofit2.Response

class CellRemoteDataSourceImpl: CellRemoteDataSource {
    override suspend fun getAllCells(userId: String): Response<List<Cell>> {
        return CellServiceRequestFactory.retrofit.getCellListFromUser(userId)
    }

    override suspend fun deleteCell(userId: String, cellId: String): Response<Unit> {
        return CellServiceRequestFactory.retrofit.deleteCell(userId, cellId)
    }
}