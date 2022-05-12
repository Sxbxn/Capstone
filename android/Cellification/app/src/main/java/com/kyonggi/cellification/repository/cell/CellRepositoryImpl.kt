package com.kyonggi.cellification.repository.cell

import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.model.cell.RequestCell
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.data.model.cell.ResponseSpecificUserCell
import com.kyonggi.cellification.repository.cell.datasource.CellLocalDataSource
import com.kyonggi.cellification.repository.cell.datasource.CellRemoteDataSource
import com.kyonggi.cellification.utils.APIResponse
import okhttp3.MultipartBody

class CellRepositoryImpl(
    private val cellRemoteDataSource: CellRemoteDataSource,
    private val cellLocalDataSource: CellLocalDataSource
):CellRepository {
    //Remote API
    override suspend fun makeCellTest(token:String, requestCell: RequestCell, userid: String): APIResponse<ResponseCell> {
        val response = cellRemoteDataSource.makeCell(token, requestCell, userid)
        if(response.isSuccessful){
            response.body()?.let{
                return APIResponse.Success(it)
            }
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun getCellListFromUser(token:String, userid: String): APIResponse<MutableList<ResponseCell>> {
        val response = cellRemoteDataSource.getCellListFromUser(token, userid)
        if(response.isSuccessful){
            response.body()?.let{
                return APIResponse.Success(it)
            }
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun getCellInfoFromCellID(token:String, cellid: String): APIResponse<ResponseCell> {
        val response = cellRemoteDataSource.getCellInfoFromCellID(token, cellid)
        if(response.isSuccessful){
            response.body()?.let{
                return APIResponse.Success(it)
            }
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun deleteAllCell(token:String, userid: String): APIResponse<Void> {
        val response = cellRemoteDataSource.deleteAllCell(token, userid)
        if(response.isSuccessful){
            return APIResponse.Success()
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun deleteSpecificCell(token:String, userid: String, cellid: String): APIResponse<Void> {
        val response = cellRemoteDataSource.deleteSpecificCell(token, userid, cellid)
        if(response.isSuccessful){
            return APIResponse.Success()
        }
        return APIResponse.Error(response.message())
    }

    /***
    -----------------------------------------------------------------------------------------------
     ***/

    //Local API
    override suspend fun getAllCells(): MutableList<Cell> {
        return cellLocalDataSource.getAllCells()
    }

    override suspend fun getCellsFromEmail(email: String): MutableList<Cell> {
        return cellLocalDataSource.getCellsFromEmail(email)
    }

    override suspend fun deleteAllLocalCell(email: String) {
        return cellLocalDataSource.deleteAllLocalCell(email)
    }

    override suspend fun deleteCell(cell: Cell) {
        return cellLocalDataSource.deleteCell(cell)
    }

    override suspend fun insertCell(cell: Cell) {
        return cellLocalDataSource.insertCell(cell)
    }
}