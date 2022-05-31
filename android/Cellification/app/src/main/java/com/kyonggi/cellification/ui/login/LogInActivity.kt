package com.kyonggi.cellification.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.databinding.ActivityLogInBinding
import com.kyonggi.cellification.ui.cell.MainActivity
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.viewmodel.UserViewModel
import com.kyonggi.cellification.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLogInBinding
    private lateinit var loading: LoadingDialog
    private lateinit var userLogin: UserLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLogInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loading = LoadingDialog(this)
        // 자동 로그인
        loginCheck()

        // 요청 취소
        loading.cancelButton().setOnClickListener {
            loading.setInvisible()
        }
        // 요청 다시시도
        loading.retryButton().setOnClickListener {
            loading.setInvisible()
            requestLogin(userLogin)
        }

        userViewModel.isValidLiveData.observe(this) { isValid ->
            binding.buttonLogin.isEnabled = isValid
        }

        with(binding) {
            // 회원가입 페이지 이동
            buttonSignin.setOnClickListener {
                startActivity(Intent(this@LogInActivity, SignInActivity::class.java).apply {
                    this.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                })
            }

            editTextLoginId.doOnTextChanged { text, _, _, _ ->
                initValidate(emailInputLayout)
                userViewModel.emailLiveData.value = text.toString()
            }
            editTextLoginPwd.doOnTextChanged { text, _, _, _ ->
                userViewModel.pwdLiveData.value = text.toString()
            }
            buttonLogin.setOnClickListener {
                userLogin =
                    UserLogin(editTextLoginId.text.toString(), editTextLoginPwd.text.toString())

                if (!validateLoginEmail(emailInputLayout, editTextLoginId.text.toString())) {
                    shake(editTextLoginId, this@LogInActivity)
                } else {
                    // 로그인 요청
                    if (getConnectivityStatus(applicationContext)) {
                        requestLogin(userLogin)
                    }
                }
            }
        }
    }

    private fun getInfo(token: String, userId: String) {
        userViewModel.getInfo(token, userId)
        userViewModel.getInfo.observe(this@LogInActivity) { response ->
            when (response) {
                is APIResponse.Success -> {
                    // success code
                    App.prefs.apply {
                        this.email = response.data?.email
                        this.name = response.data?.name
                    }
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        this.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })

//                    finish 전에 dismiss 해야 에러 안남
                    loading.dismiss()
                    finish()
                }
                is APIResponse.Error -> {
                    // error code
                    loading.setError()
                }
                is APIResponse.Loading -> {
                    // loading code
                    loading.setVisible()
                }
            }
        }
    }

    private fun requestLogin(userLogin: UserLogin) {
        userViewModel.getTokenRequest(userLogin)
        userViewModel.isLogin.observe(this@LogInActivity) { response ->
            when (response) {
                is APIResponse.Success -> {
                    // success code
                    App.prefs.apply {
                        token = response.data?.get("token")
                        userId = response.data?.get("userId")
                    }
                    App.prefs.token?.let { App.prefs.userId?.let { it1 -> getInfo(it, it1) } }
                }
                is APIResponse.Error -> {
                    // error code
                    loading.setError()
                }
                is APIResponse.Loading -> {
                    // loading code
                    loading.setVisible()
                }
            }
        }
    }

    private fun loginCheck() {
        // JWT Token 로컬에 저장되어있다면 자동 로그인
        if (!App.prefs.token.isNullOrBlank()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
