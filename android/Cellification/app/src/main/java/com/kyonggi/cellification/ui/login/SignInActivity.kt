package com.kyonggi.cellification.ui.login

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kyonggi.cellification.data.model.user.User
import com.kyonggi.cellification.databinding.ActivitySignInBinding
import com.kyonggi.cellification.ui.viewmodel.UserViewModel
import com.kyonggi.cellification.utils.APIResponse
import com.kyonggi.cellification.utils.validateEmail
import com.kyonggi.cellification.utils.validateName
import com.kyonggi.cellification.utils.validateSigninPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonSignIn.setOnClickListener {
                if (checkValidation(editTextSigninId, editTextSigninPwd, editTextSigninName)) {
                    if (editTextSigninPwd.text.toString() != editTextSigninPwdChk.text.toString()) {
                        Toast.makeText(
                            this@SignInActivity,
                            "비밀번호와 비밀번호확인이 일치하지 않습니다..",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val user = User(
                            editTextSigninId.text.toString(),
                            editTextSigninName.text.toString(),
                            editTextSigninPwd.text.toString()
                        )
                        // 회원가입 요청
                        requestSignIn(user)
                    }
                }
            }
        }

    }

    // Validation 체크
    private fun checkValidation(
        email: EditText,
        pwd: EditText,
        name: EditText
    ): Boolean {
        return validateEmail(email, this) || validateSigninPassword(pwd, this) || validateName(name, this)
    }

    // 회원가입 요청
    private fun requestSignIn(user: User) {
        userViewModel.signInUser(user)
        userViewModel.state.observe(this, Observer { response ->
            when (response) {
                is APIResponse.Success -> {
                    // success code
                }
                is APIResponse.Error -> {
                    // error code
                }
                is APIResponse.Loading -> {
                    // loading code
                }
            }
        })
    }
}