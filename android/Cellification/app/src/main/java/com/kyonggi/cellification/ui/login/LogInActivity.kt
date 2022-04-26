package com.kyonggi.cellification.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.databinding.ActivityLogInBinding
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.viewmodel.UserViewModel
import com.kyonggi.cellification.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLogInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        with(binding) {
            // 회원가입 페이지 이동
            buttonSignin.setOnClickListener {
                startActivity(Intent(this@LogInActivity, SignInActivity::class.java))
            }
            // 로그인 요청
            buttonLogin.setOnClickListener {
                if (checkValidation(editTextLoginId, editTextLoginPwd)) {
                    val userLogin =
                        UserLogin(editTextLoginId.text.toString(), editTextLoginPwd.text.toString())
                    requestLogin(userLogin)
                }
            }
        }
    }

    // Validation 체크
    private fun checkValidation(
        email: EditText,
        pwd: EditText,
    ): Boolean {
        return validateEmail(email, this) || validateLoginPassword(pwd, this)
    }

    private fun requestLogin(userLogin: UserLogin) {
        userViewModel.getTokenRequest(userLogin)
        userViewModel.isLogin.observe(this@LogInActivity, Observer { response ->
            when (response) {
                is APIResponse.Success -> {
                    // success code
                    Toast.makeText(applicationContext, response.data?.get("token"), Toast.LENGTH_SHORT).show()
                    Toast.makeText(applicationContext, response.data?.get("userid"), Toast.LENGTH_SHORT).show()
                    App.prefs.token = response.data?.get("token")
                    App.prefs.userId = response.data?.get("userId")
                }
                is APIResponse.Error -> {
                    // error code
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                }
                is APIResponse.Loading -> {
                    // loading code
                    Toast.makeText(applicationContext, "로딩중", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
