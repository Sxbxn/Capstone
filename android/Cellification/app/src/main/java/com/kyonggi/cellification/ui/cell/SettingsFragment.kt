package com.kyonggi.cellification.ui.cell

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.kyonggi.cellification.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when(preference.key) {
            "deleteCloud" -> {}
            "deleteLocal" -> {}
            "logOut" -> {}
            "signOut" -> {}
        }
        return super.onPreferenceTreeClick(preference)
    }

}