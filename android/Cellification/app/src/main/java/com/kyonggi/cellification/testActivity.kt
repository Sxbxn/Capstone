package com.kyonggi.cellification


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.kyonggi.cellification.ui.AnalysisFragment
import com.kyonggi.cellification.ui.StorageFragment

// foget password 변화 테스트

//class TestActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_analysis_navigation_drawer)
//
////        var forget:TextView = findViewById(R.id.forget)
////        forget.setOnClickListener {
////            forget.paintFlags = Paint.UNDERLINE_TEXT_FLAG
////        }
//
//    }
//}


//side bar 테스트
//class TestActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.cell_repository)
//        val mainNavigationView:NavigationView = findViewById(R.id.main_navigationView)
//
//        mainNavigationView.setNavigationItemSelectedListener(this)
//
//    }
//
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.account -> Toast.makeText(this, "login clicked", Toast.LENGTH_SHORT).show()
//        }
//        return false
//    }
//
//    override fun onBackPressed() {
//        val mainDrawerLayout:DrawerLayout = findViewById(R.id.main_drawer_layout)
//        if(mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
//            mainDrawerLayout.closeDrawers()
//            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
//            Toast.makeText(this,"back btn clicked",Toast.LENGTH_SHORT).show()
//        } else{
//            super.onBackPressed()
//        }
//    }
//}


//class TestActivity: AppCompatActivity(){
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.cell_repository)
//        val recyclerView : RecyclerView = findViewById(R.id.recycler)
//        val adaptor= Adaptor(this)
//        recyclerView.adapter = adaptor
//
//        val llm = LinearLayoutManager(this)
//        recyclerView.layoutManager = llm
//        recyclerView.setHasFixedSize(true)
//    }
//}


//bottom navigation 연결
class TestActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val fragmentAnalysis: AnalysisFragment = AnalysisFragment()
    private val fragmentStorage: StorageFragment = StorageFragment()
    private var backpressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { item ->
            ItemSelectedListener().onNavigationItemSelected(
                item
            )
        }

        val mainNavigationView: NavigationView = findViewById(R.id.main_navigationView)
        mainNavigationView.setNavigationItemSelectedListener(this)
    }

    inner class ItemSelectedListener : NavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.storage ->
                    transaction.replace(R.id.fragmentContainerView, fragmentStorage).commit()
                R.id.home ->
                    transaction.replace(R.id.fragmentContainerView, fragmentAnalysis).commit()
            }
            return true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.login -> {
                startActivity(Intent(this@TestActivity, LoginActivity::class.java))
            }
        }
        return false
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

