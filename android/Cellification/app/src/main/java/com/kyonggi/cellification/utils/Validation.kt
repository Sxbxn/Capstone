package com.kyonggi.cellification.utils

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import com.kyonggi.cellification.R

fun shake(editText: EditText, context: Context) {
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
    editText.startAnimation(animation)
    editText.requestFocus()
}

fun validateEmail(inputEmail: EditText, context: Context): Boolean {
    val email: String = inputEmail.text.toString().trim()

    if (email.isEmpty()) {
        Toast.makeText(context, "아이디가 아무것도 입력되지 않았습니다..", Toast.LENGTH_SHORT).show()
        shake(inputEmail, context)
        return false
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        Toast.makeText(context, "아이디가 이메일 형식이 아닙니다..", Toast.LENGTH_SHORT).show()
        shake(inputEmail, context)
        return false
    } else {
        return true
    }

}

fun validateLoginPassword(inputPwd: EditText, context: Context): Boolean {
    val pwd: String = inputPwd.text.toString().trim()

    if (pwd.isEmpty()) {
        Toast.makeText(context, "비밀번호가 아무것도 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
        shake(inputPwd, context)
        return false
    } else {
        return true
    }
}

fun validateSigninPassword(inputPwd: EditText, context: Context): Boolean {
    // 최소 8자리이상, 숫자, 특수문자 각각최소1개이상
    val pwdValidation = Regex("""^(?=.*?[0-9])(?=.*?[#?!@\$ %^&*-]).{8,}$""")
    val pwd: String = inputPwd.text.toString().trim()

    if (pwd.isEmpty()) {
        Toast.makeText(context, "비밀번호가 아무것도 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
        shake(inputPwd, context)
        return false
    } else if (!pwd.matches(pwdValidation)) {
        Toast.makeText(context, "비밀번호는 최소 8자리이상, 숫자, 특수문자 각각 1개이상이여야 합니다.", Toast.LENGTH_SHORT)
            .show()
        shake(inputPwd, context)
        return false
    } else {
        return true
    }
}

fun validateName(inputName: EditText, context: Context): Boolean {
    //한글과 영문만 가능
    val nameValidation = Regex("""^[가-힣a-zA-Z ]{2,20}$""")
    val name: String = inputName.text.toString().trim()

    if (name.isEmpty()) {
        Toast.makeText(context, "이름이 아무것도 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
        shake(inputName, context)
        return false
    } else if (!name.matches(nameValidation)) {
        Toast.makeText(context, "이름은 한글이나 영문만 가능합니다..", Toast.LENGTH_SHORT).show()
        shake(inputName, context)
        return false
    } else {
        return true
    }
}