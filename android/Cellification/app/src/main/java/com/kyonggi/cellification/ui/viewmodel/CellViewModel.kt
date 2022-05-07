package com.kyonggi.cellification.ui.viewmodel

import androidx.lifecycle.LiveData
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
    // 분석된 cell을 위한 것
    private val _state: MutableLiveData<APIResponse<ResponseCell>> = MutableLiveData()
    // 리사이클러 뷰에 표현하기 위한 cell list
    private val _stateList: MutableLiveData<APIResponse<MutableList<ResponseCell>>> = MutableLiveData()
    private val _deleteAndSendCell: MutableLiveData<APIResponse<Void>> = MutableLiveData()
    val state: LiveData<APIResponse<ResponseCell>>
        get() = _state
    val stateList: LiveData<APIResponse<MutableList<ResponseCell>>>
        get() = _stateList
    val deleteAndSendCell: LiveData<APIResponse<Void>>
        get() = _deleteAndSendCell

    //Local APi variable
    val _stateLocal: MutableLiveData<Cell> = MutableLiveData()
    val _stateListLocal: MutableLiveData<List<Cell>> = MutableLiveData()
    val stateLocal: LiveData<Cell>
        get() = _stateLocal
    val stateListLocal: LiveData<List<Cell>>
        get() = _stateListLocal

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

    fun makeCellTest(token:String, requestCell: RequestCell, userid: String) {
        _state.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.makeCellTest(token, requestCell, userid)
            result(response, _state)
        }
    }

    fun getCellListFromUser(token:String, userid: String) {
        _stateList.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.getCellListFromUser(token, userid)
            try {
                if (response.data != null) {
                    _stateList.postValue(response)
                } else {
                    _stateList.postValue(APIResponse.Error(response.message.toString()))
                }
            } catch (e: Exception) {
                _stateList.postValue(APIResponse.Error(e.message.toString()))
            }
        }
    }

    fun getCellInfoFromCellID(token:String, userid: String) {
        _state.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.getCellInfoFromCellID(token, userid)
            result(response, _state)
        }
    }

    fun deleteAllCell(token:String, userid: String) {
        _deleteAndSendCell.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.deleteAllCell(token, userid)
            result(response, _deleteAndSendCell)
        }
    }

    fun deleteSpecificCell(token:String, userid: String, cellid: String) {
        _deleteAndSendCell.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.deleteSpecificCell(token, userid, cellid)
            result(response, _deleteAndSendCell)
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
            result(response, _stateListLocal)
        }


    fun getCellsQueryEmail(email: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.getCellsFromEmail(email)
            result(response, _stateListLocal)
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