package com.kyonggi.cellification.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.app.AlertDialog
import android.view.View
import com.kyonggi.cellification.R

class CustomAlertDialog(context: Context) : AlertDialog(context) {

    private lateinit var dialog: AlertDialog

    fun init(title: String) {
        dialog = Builder(context, R.style.DeleteDialog)
            .setMessage(title)
            .setPositiveButton("삭제") { _, _ -> }
            .setNegativeButton("취소") { _, _ -> }
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }
    fun initWithView(title: String, viewId: View){

        dialog = Builder(context, R.style.DeleteDialog)
            .setMessage(title)
            .setView(viewId)
            .setPositiveButton("확인") { _, _ -> }
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    fun getPositive(): Button {
        return dialog.getButton(BUTTON_POSITIVE)
    }

    fun getNegative(): Button {
        return dialog.getButton(BUTTON_NEGATIVE)
    }

    fun exit() {
        dialog.dismiss()
    }
}