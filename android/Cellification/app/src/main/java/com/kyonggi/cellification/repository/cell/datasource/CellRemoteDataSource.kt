package com.kyonggi.cellification.repository.cell.datasource

import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.data.model.cell.ResponseSpecificUserCell
import okhttp3.MultipartBody
import retrofit2.Response

interface CellRemoteDataSource {
//    suspend fun getAllCells(): Response<List<Cell>>
    suspend fun makeCell(userid: String): Response<ResponseCell>
    suspend fun getCellListFromUser(userid: String): Response<ResponseSpecificUserCell>
    suspend fun getCellInfoFromCellID(cellid: String): Response<ResponseCell>
    suspend fun deleteAllCell(userid: String): Response<Void>
    suspend fun deleteSpecificCell(userid: String, cellid: String): Response<Void>
    suspend fun sendCellImage(userid:String, image: MultipartBody.Part): Response<Void>
}