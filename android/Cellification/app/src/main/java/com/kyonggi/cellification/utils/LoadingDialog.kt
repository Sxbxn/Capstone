package com.kyonggi.cellification.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.kyonggi.cellification.R

class LoadingDialog (context: Context) : Dialog(context){

    init {
        setCanceledOnTouchOutside(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.dialog_loading)
    }

    fun setVisible() {
        this.show()
    }

    fun setInvisible() {
        this.dismiss()
    }
}