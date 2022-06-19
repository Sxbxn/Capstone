package com.kyonggi.cellification.ui.di

import android.app.Application
import com.kyonggi.cellification.utils.PreferenceUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        lateinit var prefs: PreferenceUtils
    }
    override fun onCreate() {
        prefs = PreferenceUtils(applicationContext)
        super.onCreate()
    }
}