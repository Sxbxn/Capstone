package com.kyonggi.cellification.ui.cell

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.kyonggi.cellification.R
import com.kyonggi.cellification.databinding.ActivityMainBinding
import com.kyonggi.cellification.ui.login.LogInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var backpressedTime: Long = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNav()
        initNavigationView()
    }

    private fun initNavigationView() {
        binding.mainNavigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_login -> {
                    startActivity(Intent(this@MainActivity, LogInActivity::class.java))
                }
                R.id.nav_setting -> {
                    changeFragment(SettingsFragment())
                }
                R.id.nav_credit -> {
                    TODO("추후 구현")
                }
            }
            true
        }
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

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    override fun onBackPressed() {
        val mainDrawerLayout: DrawerLayout = findViewById(R.id.main_drawer_layout)

        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawers()
        } else {
            if (System.currentTimeMillis() > backpressedTime + 2000) {
                backpressedTime = System.currentTimeMillis()
                Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
                finish()
            }

        }
    }
}