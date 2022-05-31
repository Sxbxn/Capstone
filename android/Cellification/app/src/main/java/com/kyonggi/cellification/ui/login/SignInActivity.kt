package com.kyonggi.cellification.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.databinding.ActivitySignInBinding
import com.kyonggi.cellification.ui.viewmodel.SignInViewModel
import com.kyonggi.cellification.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private val signInViewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding
    private lateinit var loading: LoadingDialog
    private lateinit var user: User
    private lateinit var email: String
    private lateinit var pwd: String
    private lateinit var re_pwd: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        loading = LoadingDialog(this)
        setContentView(binding.root)

        // 요청 취소
        loading.cancelButton().setOnClickListener {
            loading.setInvisible()
        }
        // 요청 다시시도
        loading.retryButton().setOnClickListener {
            loading.setInvisible()
            requestSignIn(user)
        }

        signInViewModel.isValidLiveData.observe(this) { isValid ->
            binding.buttonSignIn.isEnabled = isValid
        }

        with(binding) {
            // 로그인 페이지 이동
            buttonLogIn.setOnClickListener {
                startActivity(Intent(this@SignInActivity, LogInActivity::class.java).apply {
                    this.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                })
            }

            editTextSigninId.doOnTextChanged { text, _, _, _ ->
                initValidate(emailInputLayout)
                signInViewModel.emailLiveData.value = text.toString()
                email = text.toString()
            }

            editTextSigninPwd.doOnTextChanged { text, _, _, _ ->
                initValidate(pwdInputLayout)
                signInViewModel.pwdLiveData.value = text.toString()
                pwd = text.toString()
            }

            editTextSigninPwdChk.doOnTextChanged { text, _, _, _ ->
                initValidate(rePwdInputLayout)
                signInViewModel.repwdLiveData.value = text.toString()
                re_pwd = text.toString()
            }

            editTextSigninName.doOnTextChanged { text, _, _, _ ->
                initValidate(nameInputLayout)
                signInViewModel.nameLiveData.value = text.toString()
                name = text.toString()
            }
            buttonSignIn.setOnClickListener {
                user = User(email, name, pwd)
                if (checkValidation()) {
                    if (getConnectivityStatus(applicationContext)) {
                        // 회원가입 요청
                        requestSignIn(user)
                    }
                }
            }
        }
    }

    private fun checkValidation(): Boolean {
        var check = true
        with(binding) {
            if (!validateEmail(emailInputLayout, email)) {
                shake(editTextSigninId, this@SignInActivity)
                check = false
            }
            if (!validateSignInPassword(pwdInputLayout, pwd)) {
                shake(editTextSigninPwd, this@SignInActivity)
                check = false
            }
            if (!validateSignInRePassword(rePwdInputLayout, pwd, re_pwd)) {
                shake(editTextSigninPwdChk, this@SignInActivity)
                check = false
            }
            if (!validateName(nameInputLayout, name)) {
                shake(editTextSigninName, this@SignInActivity)
                check = false
            }
            return check
        }
    }

    // 회원가입 요청
    private fun requestSignIn(user: User) {
        signInViewModel.signInUser(user)
        signInViewModel.state.observe(this) { response ->
            when (response) {
                is APIResponse.Success -> {
                    // success code
                    loading.dismiss()
                    startActivity(Intent(this, LogInActivity::class.java).apply {
                        this@SignInActivity.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    })
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
}