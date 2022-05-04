package com.kyonggi.cellification.repository.cell.datasource

import com.kyonggi.cellification.data.model.cell.RequestCell
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.data.model.cell.ResponseSpecificUserCell
import okhttp3.MultipartBody
import retrofit2.Response

interface CellRemoteDataSource {
//    suspend fun getAllCells(): Response<List<Cell>>
    suspend fun makeCell(token:String, requestCell: RequestCell, userid: String): Response<ResponseCell>
    suspend fun getCellListFromUser(token:String, userid: String): Response<List<ResponseCell>>
    suspend fun getCellInfoFromCellID(token:String, cellid: String): Response<ResponseCell>
    suspend fun deleteAllCell(token:String, userid: String): Response<Void>
    suspend fun deleteSpecificCell(token:String, userid: String, cellid: String): Response<Void>

}