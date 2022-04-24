package com.kyonggi.cellification.repository.cell

import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.data.model.cell.ResponseSpecificUserCell
import com.kyonggi.cellification.utils.APIResponse
import okhttp3.MultipartBody

interface CellRepository {
    suspend fun makeCellTest(userid: String): APIResponse<ResponseCell>
    suspend fun getCellListFromUser(userid: String): APIResponse<ResponseSpecificUserCell>
    suspend fun getCellInfoFromCellID(cellid: String): APIResponse<ResponseCell>
    suspend fun deleteAllCell(userid: String): APIResponse<Void>
    suspend fun deleteSpecificCell(userid: String, cellid: String): APIResponse<Void>
    suspend fun sendCellImage(userid:String, image: MultipartBody.Part): APIResponse<Void>
}