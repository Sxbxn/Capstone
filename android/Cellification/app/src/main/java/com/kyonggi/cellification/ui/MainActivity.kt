package com.kyonggi.cellification.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kyonggi.cellification.R
import com.kyonggi.cellification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        super.onCreate(savedInstanceState)
        setContentView(view)
    }
}