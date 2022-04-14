package com.kyonggi.cellification.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.kyonggi.cellification.databinding.ActivityLogInBinding
import com.kyonggi.cellification.utils.validateEmail
import com.kyonggi.cellification.utils.validateLoginPassword

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLogInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val signinButton: Button = binding.buttonSignin
        val loginButton: Button = binding.buttonLogin
        val idEditText: EditText = binding.editTextLoginId
        val pwdEditText: EditText = binding.editTextLoginPwd

        signinButton.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignInActivity::class.java))
        }
        loginButton.setOnClickListener {
            val resultEmailValidation = validateEmail(idEditText, applicationContext)
            val resultPwdValidation = validateLoginPassword(pwdEditText, applicationContext)
        }
    }
}
