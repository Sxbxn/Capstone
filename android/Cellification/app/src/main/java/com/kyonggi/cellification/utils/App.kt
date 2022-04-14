package com.kyonggi.cellification.utils

import android.app.Application

class App: Application() {
    companion object {
        lateinit var prefs: PreferenceUtils
    }
    override fun onCreate() {
        prefs = PreferenceUtils(applicationContext)
        super.onCreate()
    }
}