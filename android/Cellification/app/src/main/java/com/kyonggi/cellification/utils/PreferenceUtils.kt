package com.kyonggi.cellification.utils

import android.app.Activity
import android.content.Context

class PreferenceUtils(context: Context) {
    private val prefName="prefs"
    private val prefs=context.getSharedPreferences(prefName, Activity.MODE_PRIVATE )

    var token:String?
        get() = prefs.getString("token", null)
        set(value){
            prefs.edit().putString("token", value).apply()
        }
    var userId:String?
        get() = prefs.getString("userId", null)
        set(value) {
            prefs.edit().putString("userId", value).apply()
        }
    var email:String?
        get() = prefs.getString("email", null)
        set(value) {
            prefs.edit().putString("email", value).apply()
        }
    var name:String?
        get() = prefs.getString("name", null)
        set(value) {
            prefs.edit().putString("name", value).apply()
        }


    fun clear() {
        prefs.edit().clear().apply()
    }
}