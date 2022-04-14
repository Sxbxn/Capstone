package com.kyonggi.cellification

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kyonggi.cellification.databinding.ActivityLogInBinding
import com.kyonggi.cellification.databinding.ActivitySignInBinding
import com.kyonggi.cellification.utils.validateEmail
import com.kyonggi.cellification.utils.validateName
import com.kyonggi.cellification.utils.validateSigninPassword

class SigninActivity : AppCompatActivity() {
    private lateinit var  binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root

        super.onCreate(savedInstanceState)
        setContentView(view)

        val signinButton: Button = binding.buttonSignIn
        val idEditText: EditText = binding.editTextSigninId
        val pwdEditText: EditText = binding.editTextSigninPwd
        val pwdChkEditText: EditText = binding.editTextSigninPwdChk
        val nameEditText: EditText = binding.editTextSigninName

        signinButton.setOnClickListener {
            val resultEmailValidation = validateEmail(idEditText, applicationContext)
            val resultPwdValidation = validateSigninPassword(pwdEditText, applicationContext)
            val resultNameValidation = validateName(nameEditText, applicationContext)
            if (pwdEditText.text.toString() != pwdChkEditText.text.toString())
                Toast.makeText(this, "비밀번호와 비밀번호확인이 일치하지 않습니다..", Toast.LENGTH_SHORT).show()
        }
    }
}