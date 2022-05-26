package com.kyonggi.cellification.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Button
import com.kyonggi.cellification.R
import com.kyonggi.cellification.databinding.DialogLoadingBinding

class LoadingDialog (context: Context) : Dialog(context){

    private var binding: DialogLoadingBinding
    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setVisible() {
        this.show()
//        binding.pgbar.isIndeterminate = true
    }

    fun setInvisible() {
        this.dismiss()
    }

    fun setError() {
        with(binding) {
            loadingLayout.visibility = View.GONE
            retryButton.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE
        }
    }

    fun retryButton(): Button = binding.retryButton

    fun cancelButton(): Button = binding.cancelButton
}