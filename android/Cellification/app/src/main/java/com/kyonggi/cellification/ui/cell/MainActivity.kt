package com.kyonggi.cellification.ui.cell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kyonggi.cellification.R
import com.kyonggi.cellification.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNav()
    }

    private fun initBottomNav() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        changeFragment(AnalysisFragment())
                    }
                    R.id.storage -> {
                        changeFragment(StorageFragment())
                    }
                    R.id.settting -> {
                        changeFragment(SettingsFragment())
                    }
                }
            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.home
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}