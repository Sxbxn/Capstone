package com.kyonggi.cellification.data.remote.service

import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.model.cell.ResponseCell
import retrofit2.Response
import retrofit2.http.*

interface CellService {

//    @GET("/capstone-cell-service/cells/")
//    suspend fun getAllCells(): Response<List<Cell>>

    //cell 생성 testing
    @POST("/capstone-cell-service/{userId}/cells")
    suspend fun makeCell(
        @Path("userId") userId: String
    ):Response<ResponseCell>

    // 특정 user의 Cell List 리턴
    @GET("/capstone-cell-service/{userId}/cells")
    suspend fun getCellListFromUser(
        @Path("userId") userId: String
    ): Response<ResponseCell>

    // cellid를 통한 특정 cell 조회
    @GET("/capstone-cell-service/cells/{cellId}")
    suspend fun getCellInfoFromCellID(
        @Path("cellId") cellId: String
    )
    @DELETE("/capstone-cell-service/{userId}")
    suspend fun deleteSpecificCell(
        @Path("userId") userId: String
    )
    // 특정 분석한 Cell 정보 삭제
    @DELETE("/capstone-cell-service/{userId}/{cellId}")
    suspend fun deleteCell(
        @Path("userId") userId: String,
        @Path("cellId") cellId: String
    ): Response<Void>

    // cell 이미지 보내기(분석)


}