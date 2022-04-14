package com.kyonggi.cellification.data.remote.service

import com.kyonggi.cellification.data.model.cell.Cell
import retrofit2.Response
import retrofit2.http.*

interface CellService {

//    @GET("/capstone-cell-service/cells/")
//    suspend fun getAllCells(): Response<List<Cell>>

    // 특정 user의 Cell List 리턴
    @GET("/capstone-cell-service/{userId}/cells")
    suspend fun getCellListFromUser(
        @Path("userId") userId: String
    ): Response<List<Cell>>

    // 특정 분석한 Cell 정보 삭제
    @DELETE("/capstone-cell-service/{userId}/{cellId}")
    suspend fun deleteCell(
        @Path("userId") userId: String,
        @Path("cellId") cellId: String
    ): Response<Unit>
}