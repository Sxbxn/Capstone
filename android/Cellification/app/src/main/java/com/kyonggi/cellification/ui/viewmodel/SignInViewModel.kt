package com.kyonggi.cellification.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyonggi.cellification.data.model.user.ResponseUser
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.repository.user.UserRepository
import com.kyonggi.cellification.utils.APIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    val state: MutableLiveData<APIResponse<ResponseUser>> = MutableLiveData()
    val emailLiveData: MutableLiveData<String> = MutableLiveData()
    val pwdLiveData: MutableLiveData<String> = MutableLiveData()
    val repwdLiveData: MutableLiveData<String> = MutableLiveData()
    val nameLiveData: MutableLiveData<String> = MutableLiveData()
    val isValidLiveData: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        // 입력이 valid 하지않으면 버튼 disabled
        this.value = false

        addSource(emailLiveData) {
            this.value = validateForm()
        }

        addSource(pwdLiveData) {
            this.value = validateForm()
        }

        addSource(repwdLiveData) {
            this.value = validateForm()
        }

        addSource(nameLiveData) {
            this.value = validateForm()
        }

    }
    private fun validateForm(): Boolean {
        return !emailLiveData.value.isNullOrBlank() && !pwdLiveData.value.isNullOrBlank()
                && !repwdLiveData.value.isNullOrBlank() && !nameLiveData.value.isNullOrBlank()
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
}