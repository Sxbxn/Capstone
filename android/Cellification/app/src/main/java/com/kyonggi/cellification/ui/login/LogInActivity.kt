package com.kyonggi.cellification.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.kyonggi.cellification.data.model.user.UserLogin
import com.kyonggi.cellification.data.remote.api.UserServiceRequestFactory
import com.kyonggi.cellification.databinding.ActivityLogInBinding
import com.kyonggi.cellification.utils.getConnectivityStatus
import com.kyonggi.cellification.utils.validateEmail
import com.kyonggi.cellification.utils.validateLoginPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val signinButton: Button = binding.buttonSignin
        val loginButton: Button = binding.buttonLogin
        val idEditText: EditText = binding.editTextLoginId
        val pwdEditText: EditText = binding.editTextLoginPwd

        signinButton.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignInActivity::class.java))
        }
        loginButton.setOnClickListener {
            val networkStatus = getConnectivityStatus(applicationContext)
            val login = UserLogin(idEditText.text.toString(), pwdEditText.text.toString())

            if (networkStatus) {
                val resultEmailValidation = validateEmail(idEditText, applicationContext)
                val resultPwdValidation = validateLoginPassword(pwdEditText, applicationContext)

                if (resultEmailValidation && resultPwdValidation) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = UserServiceRequestFactory.retrofit.loginUser(login)
                        if (response.isSuccessful) {
                            Log.d("info", response.headers().toString())
                            Log.d("info", response.body().toString())
                        }
                    }
                }
            }
        }
    }
}
