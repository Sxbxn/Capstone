package com.kyonggi.cellification.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.model.cell.RequestCell
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.data.model.cell.ResponseSpecificUserCell
import com.kyonggi.cellification.repository.cell.CellRepository
import com.kyonggi.cellification.utils.APIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class CellViewModel @Inject constructor(
    private val cellRepository: CellRepository
) : ViewModel() {

    //Remote API variable
    val state: MutableLiveData<APIResponse<ResponseCell>> = MutableLiveData()
    val stateList: MutableLiveData<APIResponse<List<ResponseCell>>> = MutableLiveData()
    val stateSpecificUserCell: MutableLiveData<APIResponse<ResponseSpecificUserCell>> =
        MutableLiveData()
    val deleteAndSendCell: MutableLiveData<APIResponse<Void>> = MutableLiveData()

    //Local APi variable
    val stateLocal: MutableLiveData<Cell> = MutableLiveData()
    val stateListLocal: MutableLiveData<List<Cell>> = MutableLiveData()

    // Remote API
    private fun <T> result(response: APIResponse<T>, livedata: MutableLiveData<APIResponse<T>>) {
        try {
            if (response.data != null) {
                livedata.postValue(response)
            } else {
                livedata.postValue(APIResponse.Error(response.message.toString()))
            }
        } catch (e: Exception) {
            livedata.postValue(APIResponse.Error(e.message.toString()))
        }
    }

    fun makeCellTest(requestCell: RequestCell, userid: String) {
        state.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.makeCellTest(requestCell, userid)
            result(response, state)
        }
    }

    fun getCellListFromUser(userid: String) {
        stateList.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.getCellListFromUser(userid)
            try {
                if (response.data != null) {
                    stateList.postValue(response)
                } else {
                    stateList.postValue(APIResponse.Error(response.message.toString()))
                }
            } catch (e: Exception) {
                stateList.postValue(APIResponse.Error(e.message.toString()))
            }
        }
    }

    fun getCellInfoFromCellID(userid: String) {
        state.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.getCellInfoFromCellID(userid)
            result(response, state)
        }
    }

    fun deleteAllCell(userid: String) {
        deleteAndSendCell.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.deleteAllCell(userid)
            result(response, deleteAndSendCell)
        }
    }

    fun deleteSpecificCell(userid: String, cellid: String) {
        deleteAndSendCell.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.deleteSpecificCell(userid, cellid)
            result(response, deleteAndSendCell)
        }
    }


    // Local API
    private fun result(response: List<Cell>, livedata: MutableLiveData<List<Cell>>) {
        try {
            if (response != null) {
                livedata.postValue(response)
            } else {
                livedata.postValue(response)
            }
        } catch (e: Exception) {
            livedata.postValue(response)
        }
    }

    fun getAllCells() =
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.getAllCells()
            result(response, stateListLocal)
        }


    fun getCellsQueryEmail(email: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.getCellsFromEmail(email)
            result(response, stateListLocal)
        }


    fun deleteAllLocalCell(email: String) =
        viewModelScope.launch(Dispatchers.IO) {
            cellRepository.deleteAllLocalCell(email)
        }

    fun deleteCell(cell: Cell) =
        viewModelScope.launch(Dispatchers.IO) {
            cellRepository.deleteCell(cell)
        }

    fun insertCell(cell: Cell) =
        viewModelScope.launch(Dispatchers.IO) {
            cellRepository.insertCell(cell)
        }
}