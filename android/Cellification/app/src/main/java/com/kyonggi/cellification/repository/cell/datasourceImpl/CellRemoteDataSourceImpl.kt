package com.kyonggi.cellification.repository.cell.datasourceImpl

import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.remote.api.CellServiceRequestFactory
import com.kyonggi.cellification.repository.cell.datasource.CellRemoteDataSource
import retrofit2.Response

//class CellRemoteDataSourceImpl: CellRemoteDataSource {
//
//    override suspend fun getAllCells(): Response<List<Cell>> {
//       return CellServiceRequestFactory.retrofit.getAllCells()
//    }
//
//    override suspend fun getCell(cellid: Int): Response<Cell> {
//        return CellServiceRequestFactory.retrofit.getCell(cellid)
//    }
//
//    override suspend fun deleteCell(cellid: Int): Response<Unit> {
//        return CellServiceRequestFactory.retrofit.deleteCell(cellid)
//    }
//}
