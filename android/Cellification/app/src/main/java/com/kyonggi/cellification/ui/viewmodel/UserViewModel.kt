package com.kyonggi.cellification.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.repository.user.UserRepository
import com.kyonggi.cellification.utils.APIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    // request state
    val isLogin: MutableLiveData<APIResponse<Headers>> = MutableLiveData()
    val withdrawal: MutableLiveData<APIResponse<Void>> = MutableLiveData()
    val sendCell: MutableLiveData<APIResponse<String>> = MutableLiveData()
    val emailLiveData: MutableLiveData<String> = MutableLiveData()
    val pwdLiveData: MutableLiveData<String> = MutableLiveData()
    val isValidLiveData: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        // 입력이 valid 하지않으면 버튼 disabled
        this.value = false

        addSource(emailLiveData) {
            this.value = validateForm()
        }

        addSource(pwdLiveData) {
            this.value = validateForm()
        }
    }
    private fun validateForm(): Boolean {
        return !emailLiveData.value.isNullOrBlank() && !pwdLiveData.value.isNullOrBlank()
    }

    fun getTokenRequest(userLogin: UserLogin) {
        isLogin.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAccessToken(userLogin)
            try {
                if (response.data != null) {
                    isLogin.postValue(response)
                } else {
                    isLogin.postValue(APIResponse.Error(response.message.toString()))
                }
            } catch (e: Exception) {
                isLogin.postValue(APIResponse.Error(e.message.toString()))
            }
        }
    }

    fun withdrawalUser(token: String, userId: String) {
        withdrawal.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.withdrawalUSer(token, userId)
            withdrawal.postValue(response)
        }
    }
    fun sendCellImage(token:String, body: MultipartBody.Part) {
        sendCell.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.sendCellImage(token, body)
        }
    }
}