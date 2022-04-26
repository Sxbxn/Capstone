package com.kyonggi.cellification.repository.cell

import com.kyonggi.cellification.data.model.cell.RequestCell
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.data.model.cell.ResponseSpecificUserCell
import com.kyonggi.cellification.repository.cell.datasource.CellRemoteDataSource
import com.kyonggi.cellification.utils.APIResponse
import okhttp3.MultipartBody

class CellRepositoryImpl(
    private val cellRemoteDataSource: CellRemoteDataSource
):CellRepository {
    override suspend fun makeCellTest(requestCell: RequestCell, userid: String): APIResponse<ResponseCell> {
        val response = cellRemoteDataSource.makeCell(requestCell, userid)
        if(response.isSuccessful){
            response.body()?.let{
                return APIResponse.Success(it)
            }
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun getCellListFromUser(userid: String): APIResponse<List<ResponseCell>> {
        val response = cellRemoteDataSource.getCellListFromUser(userid)
        if(response.isSuccessful){
            response.body()?.let{
                return APIResponse.Success(it)
            }
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun getCellInfoFromCellID(cellid: String): APIResponse<ResponseCell> {
        val response = cellRemoteDataSource.getCellInfoFromCellID(cellid)
        if(response.isSuccessful){
            response.body()?.let{
                return APIResponse.Success(it)
            }
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun deleteAllCell(userid: String): APIResponse<Void> {
        val response = cellRemoteDataSource.deleteAllCell(userid)
        if(response.isSuccessful){
            return APIResponse.Success()
        }
        return APIResponse.Error(response.message())
    }

    override suspend fun deleteSpecificCell(userid: String, cellid: String): APIResponse<Void> {
        val response = cellRemoteDataSource.deleteSpecificCell(userid, cellid)
        if(response.isSuccessful){
            return APIResponse.Success()
        }
        return APIResponse.Error(response.message())
    }

}