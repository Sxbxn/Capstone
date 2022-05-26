package com.kyonggi.cellification.data.remote.service

import com.kyonggi.cellification.data.model.cell.ResponseCell
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CellService {
    //cell 생성 testing     이제 멀티파트 추가해서 ㄱ이미지 보내기
    @Multipart
    @POST("/capstone-cell-service/{userId}/cells")
    suspend fun makeCell(
        @Header ("Authorization") token :String,
        @Part cellImage: MultipartBody.Part?,
        @Path("userId") userId: String
    ):Response<ResponseCell>

    // 특정 user의 Cell List 리턴
    @GET("/capstone-cell-service/{userId}/cells")
    suspend fun getCellListFromUser(
        @Header ("Authorization") token :String,
        @Path("userId") userId: String
    ): Response<MutableList<ResponseCell>>

    // cellid를 통한 특정 cell 조회
    @GET("/capstone-cell-service/cells/{cellId}")
    suspend fun getCellInfoFromCellID(
        @Header ("Authorization") token :String,
        @Path("cellId") cellId: String
    ): Response<ResponseCell>

    //특정 user의 전체 cell 삭제
    @DELETE("/capstone-cell-service/{userId}")
    suspend fun deleteAllCell(
        @Header ("Authorization") token :String,
        @Path("userId") userId: String
    ): Response<Void>

    // 특정 user의 특정 분석한 Cell 정보 삭제
    @DELETE("/capstone-cell-service/{userId}/{cellId}")
    suspend fun deleteSpecificCell(
        @Header ("Authorization") token :String,
        @Path("userId") userId: String,
        @Path("cellId") cellId: String
    ): Response<Void>


}