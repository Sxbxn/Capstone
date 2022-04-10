package com.kyonggi.cellification


import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

// foget password 변화 테스트

//class TestActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_loading)
//
////        var forget:TextView = findViewById(R.id.forget)
////        forget.setOnClickListener {
////            forget.paintFlags = Paint.UNDERLINE_TEXT_FLAG
////        }
//
//    }
//}


//side bar 테스트
class TestActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_navigation_drawer)
        val mainNavigationView:NavigationView = findViewById(R.id.main_navigationView)

        mainNavigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.account -> Toast.makeText(this, "login clicked", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override fun onBackPressed() {
        val mainDrawerLayout:DrawerLayout = findViewById(R.id.main_drawer_layout)
        if(mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mainDrawerLayout.closeDrawers()
            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
            Toast.makeText(this,"back btn clicked",Toast.LENGTH_SHORT).show()
        } else{
            super.onBackPressed()
        }
    }
}

