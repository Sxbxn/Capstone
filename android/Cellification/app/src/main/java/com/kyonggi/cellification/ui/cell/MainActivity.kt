package com.kyonggi.cellification.ui.cell

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kyonggi.cellification.R
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.databinding.ActivityMainBinding
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.login.LogInActivity
import com.kyonggi.cellification.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var backPressedTime: Long = 0
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainDrawerLayout: DrawerLayout
    private val userViewModel: UserViewModel by viewModels()
    private val token = App.prefs.token
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainDrawerLayout = binding.mainDrawerLayout
        initBottomNav()
        initNavigationView()
    }

    private fun initNavigationView() {
        binding.mainNavigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_login -> {
                    if (binding.mainNavigationView.menu.get(0).title == "LOG IN") {
                        startActivity(Intent(this@MainActivity, LogInActivity::class.java))
                    } else {
                        App.prefs.clear()
                        handler.postDelayed({
                            mainDrawerLayout.closeDrawer(Gravity.LEFT)
                        }, 100)
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                        finish()
                    }
                }
                R.id.nav_setting -> {
                    handler.postDelayed({
                        mainDrawerLayout.closeDrawer(Gravity.LEFT)
                    }, 100)
                    changeFragment(SettingsFragment())
                }
                R.id.nav_credit -> {

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
    fun changeFragment(fragment: Fragment, data: ResponseCell) {
        val bundle = Bundle()
        bundle.putInt("totalCell",data.totalCell)
        bundle.putInt("liveCell",data.liveCell)
        bundle.putInt("deadCell",data.deadCell)
        bundle.putDouble("viability",data.viability)
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
    override fun onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawers()
        } else {
            if (System.currentTimeMillis() > backPressedTime + 2000) {
                backPressedTime = System.currentTimeMillis()
                Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
                finish()
            }
        }
    }
}