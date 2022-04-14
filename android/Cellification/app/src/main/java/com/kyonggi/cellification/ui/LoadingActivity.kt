package com.kyonggi.cellification.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.kyonggi.cellification.R
import com.kyonggi.cellification.TestActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        goToMain()
    }

    private fun goToMain() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)


    }
}