package com.kyonggi.cellification.ui.cell

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.kyonggi.cellification.R
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.viewmodel.UserViewModel
import com.kyonggi.cellification.utils.APIResponse
import androidx.lifecycle.Observer
import com.kyonggi.cellification.ui.login.LogInActivity
import com.kyonggi.cellification.ui.viewmodel.CellViewModel
import com.kyonggi.cellification.utils.CustomAlertDialog
import com.kyonggi.cellification.utils.LoadingDialog

class SettingsFragment : PreferenceFragmentCompat() {

    private val cellViewModel: CellViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var loading: LoadingDialog
    private val handler = Handler(Looper.getMainLooper())
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
        val dialog = CustomAlertDialog(requireContext())
        dialog.init("삭제 하시겠습니까?")
        dialog.getPositive().setOnClickListener {
            if (key == "deleteCloud")
                deleteCloud()
            else if (key == "deleteLocal")
                deleteLocal()
            handler.postDelayed({
                dialog.exit()
            }, 400)
        }
        dialog.getNegative().setOnClickListener {
            handler.postDelayed({
                dialog.exit()
            }, 400)
        }
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

    private fun deleteCloud() {
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

    private fun deleteLocal() {
        cellViewModel.deleteAllLocalCell(App.prefs.userId.toString())
        cellViewModel.stateListLocal.observe(this, Observer {
            Toast.makeText(requireContext(), "현 계정 Local 전체 Cell 삭제 성공", Toast.LENGTH_SHORT).show()
        })
    }

    private fun logOut() {
        val dialog = CustomAlertDialog(requireContext())
        dialog.init("로그아웃 하시겠습니까?")
        dialog.getPositive().setOnClickListener {
            App.prefs.clear()
            Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LogInActivity::class.java))
        }
        dialog.getNegative().setOnClickListener {
            handler.postDelayed({
                dialog.exit()
            }, 400)
        }
    }

    private fun signOut(userId: String) {
        val dialog = CustomAlertDialog(requireContext())
        dialog.init("회원탈퇴 하시겠습니까?")
        dialog.getPositive().setOnClickListener {
            val token = "Bearer " + App.prefs.token
            userViewModel.withdrawalUser(token, userId)
            userViewModel.withdrawal.observe(this, Observer { response ->
                when (response) {
                    is APIResponse.Success -> {
                        App.prefs.clear()
                        Toast.makeText(requireContext(), "회원탈퇴 되었습니다.", Toast.LENGTH_SHORT).show()
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
        dialog.getNegative().setOnClickListener {
            handler.postDelayed({
                dialog.exit()
            }, 400)
        }
    }
}