package com.kyonggi.cellification.ui.cell

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.kyonggi.cellification.R
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.viewmodel.UserViewModel
import com.kyonggi.cellification.utils.APIResponse
import androidx.lifecycle.Observer
import com.kyonggi.cellification.ui.login.LogInActivity
import com.kyonggi.cellification.utils.LoadingDialog

class SettingsFragment : PreferenceFragmentCompat() {

    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var loading: LoadingDialog

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        loading = LoadingDialog(requireContext())

        // 요청 취소
        loading.cancelButton().setOnClickListener {
            loading.setInvisible()
        }
        // 요청 다시시도
        loading.retryButton().setOnClickListener {
            loading.setInvisible()
            App.prefs.userId?.let { signOut(it) }
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when(preference.key) {
            "deleteCloud" -> {}
            "deleteLocal" -> {}
            "logOut" -> {
                logOut()
            }
            "signOut" -> {
                App.prefs.userId?.let { signOut(it) }
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun logOut() {
        App.prefs.clear()
        startActivity(Intent(requireContext(), LogInActivity::class.java))
    }

    private fun signOut(userId: String) {
        val token = "Bearer " + App.prefs.token
        userViewModel.withdrawalUser(token, userId)
        userViewModel.withdrawal.observe(this, Observer { response ->
            when (response) {
                is APIResponse.Success -> {
                    App.prefs.clear()
                    startActivity(Intent(requireContext(), LogInActivity::class.java).apply {
                        this.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    })
                }
                is APIResponse.Error -> {
                    loading.setError()
                }
                is APIResponse.Loading -> {
                    loading.setVisible()
                }

            }
        })
    }
}