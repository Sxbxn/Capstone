package com.kyonggi.cellification.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyonggi.cellification.data.model.user.ResponseUser
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.repository.user.UserRepository
import com.kyonggi.cellification.utils.APIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.Headers
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    // request state
    val state: MutableLiveData<APIResponse<ResponseUser>> = MutableLiveData()
    val isLogin: MutableLiveData<APIResponse<Headers>> = MutableLiveData()
    val withdrawal: MutableLiveData<APIResponse<Void>> = MutableLiveData()
    val sendCell: MutableLiveData<APIResponse<String>> = MutableLiveData()

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
   fun signInUser(user: User) {
       state.value = APIResponse.Loading()
       viewModelScope.launch(Dispatchers.IO)  {
           val response = repository.signInUser(user)
           try {
               if (response.data != null) {
                   state.postValue(response)
               } else {
                   state.postValue(APIResponse.Error(response.message.toString()))
               }
           } catch (e: Exception) {
               state.postValue(APIResponse.Error(e.message.toString()))
           }
       }
   }

    fun getTokenRequest(userLogin: UserLogin) {
        isLogin.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAccessToken(userLogin)
            if (response.data != null) {
                isLogin.postValue(response)
            } else {
                isLogin.postValue(APIResponse.Error("에러 발생"))
            }
        }
    }

    fun withdrawalUser(userId: String) {
        withdrawal.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.withdrawalUSer(userId)
            withdrawal.postValue(response)
        }
    }
    fun sendCellImage( body: MultipartBody.Part) {
        sendCell.value = APIResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.sendCellImage( body)
            result(response, sendCell)
        }
    }
}