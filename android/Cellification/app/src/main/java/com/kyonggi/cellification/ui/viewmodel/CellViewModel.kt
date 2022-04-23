package com.kyonggi.cellification.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val state: MutableLiveData<APIResponse<ResponseCell>> = MutableLiveData()
    private val stateSpecificUserCell: MutableLiveData<APIResponse<ResponseSpecificUserCell>> =
        MutableLiveData()
    private val deleteAndSendCell: MutableLiveData<APIResponse<Void>> = MutableLiveData()

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

    fun makeCellTest(userid: String) {
        state.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.makeCellTest(userid)
            result(response, state)
        }
    }

    fun getCellListFromUser(userid: String) {
        stateSpecificUserCell.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.getCellListFromUser(userid)
            result(response, stateSpecificUserCell)
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

    fun sendCellImage(body: MultipartBody.Part) {
        deleteAndSendCell.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = cellRepository.sendCellImage(body)
            result(response, deleteAndSendCell)
        }
    }
}