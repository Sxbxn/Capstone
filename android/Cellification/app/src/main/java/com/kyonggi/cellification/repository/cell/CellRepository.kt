package com.kyonggi.cellification.repository.cell

import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.model.cell.RequestCell
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.data.model.cell.ResponseSpecificUserCell
import com.kyonggi.cellification.utils.APIResponse
import okhttp3.MultipartBody

interface CellRepository {
    //Remote API
    suspend fun makeCellTest(token:String, requestCell: RequestCell, userid: String): APIResponse<ResponseCell>
    suspend fun getCellListFromUser(token:String, userid: String): APIResponse<MutableList<ResponseCell>>
    suspend fun getCellInfoFromCellID(token:String, cellid: String): APIResponse<ResponseCell>
    suspend fun deleteAllCell(token:String, userid: String): APIResponse<Void>
    suspend fun deleteSpecificCell(token:String, userid: String, cellid: String): APIResponse<Void>

    //Local API
    suspend fun getAllCells(): MutableList<Cell>
    suspend fun getCellsFromEmail(email: String): MutableList<Cell>
    suspend fun deleteAllLocalCell(email: String)
    suspend fun deleteCell(cell: Cell)
    suspend fun insertCell(cell: Cell)
}