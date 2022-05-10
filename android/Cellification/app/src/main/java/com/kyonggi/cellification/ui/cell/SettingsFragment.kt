package com.kyonggi.cellification.ui.cell

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.kyonggi.cellification.R
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.viewmodel.UserViewModel
import com.kyonggi.cellification.utils.APIResponse
import androidx.lifecycle.Observer
import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.ui.login.LogInActivity
import com.kyonggi.cellification.ui.viewmodel.CellViewModel
import com.kyonggi.cellification.utils.LoadingDialog

class SettingsFragment : PreferenceFragmentCompat() {

    private val cellViewModel: CellViewModel by activityViewModels()
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

    private fun deleteDialogProcess(key: String) {
        val builder = AlertDialog.Builder(requireContext(), R.style.DeleteDialog)
        val dialog = builder.setMessage("삭제 하시겠습니까?")
            .setPositiveButton("삭제") { dialog, _ ->
                if (key == "deleteCloud")
                    deleteCloud(key)
                else if(key == "deleteLocal")
                    deleteLocal(key)
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.show()
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            "deleteCloud" -> {
                deleteDialogProcess(preference.key)
            }
            "deleteLocal" -> {
                deleteDialogProcess(preference.key)
            }
            "logOut" -> {
                logOut()
            }
            "signOut" -> {
                App.prefs.userId?.let { signOut(it) }
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun deleteCloud(key: String) {
        val token = "Bearer " + App.prefs.token
        cellViewModel.deleteAllCell(token, App.prefs.userId.toString())
        cellViewModel.deleteAndSendCell.observe(this, Observer {
            when (it) {
                /**전체삭제시 data가 없어지게 된다. APIResponse 코드상 data가 null이면
                 * error를 반환하기에 전체삭제로직에서는 반대로 error가 떠야 전체삭제가 정상적으로 이루어진 것이다.
                 */
                is APIResponse.Success -> {
                    Toast.makeText(requireContext(), "삭제실패", Toast.LENGTH_SHORT).show()
                }
                is APIResponse.Error -> {
                    Toast.makeText(requireContext(), "현 계정 Cloud 전체 Cell 삭제 성공", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun deleteLocal(key: String) {
        cellViewModel.deleteAllLocalCell(App.prefs.userId.toString())
        cellViewModel.stateListLocal.observe(this, Observer {
            Toast.makeText(requireContext(), "현 계정 Local 전체 Cell 삭제 성공", Toast.LENGTH_SHORT).show()
        })
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