package com.kyonggi.cellification.data.remote.service

import com.kyonggi.cellification.data.model.cell.Cell
import retrofit2.Response
import retrofit2.http.*

interface CellService {

    @GET("/capstone-cell-service/cells/")
    suspend fun getAllCells(): Response<List<Cell>>

    // 특정 분석한 Cell정보 가져오기 
    @GET("/capstone-cell-service/cells/{cellid}")
    suspend fun getCell(
        @Path("cellid") cellid: Int
    ): Response<Cell>

    // 특정 분석한 Cell 정보 삭제
    @DELETE("/capstone-cell-service/{cellid}")
    suspend fun deleteCell(
        @Path("cellid") cellid: Int
    ): Response<Unit>
}