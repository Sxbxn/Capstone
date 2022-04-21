package com.kyonggi.cellification.ui.cell

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kyonggi.cellification.R
import com.kyonggi.cellification.TestActivity
import com.kyonggi.cellification.data.remote.api.UserServiceRequestFactory
import com.kyonggi.cellification.databinding.FragmentAnalysisBinding
import com.kyonggi.cellification.utils.GlideApp
import com.kyonggi.cellification.utils.getFullPathFromUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class AnalysisFragment : Fragment(){
    private lateinit var binding: FragmentAnalysisBinding
    private lateinit var mainActivity: TestActivity
    private lateinit var sendFile: File
    private val REQ_STORAGE_PERMISSION = 1
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                ActivityCompat.requestPermissions(
                    mainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQ_STORAGE_PERMISSION
                )
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as TestActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnImageButtonClickListener()
        setOnAnalysisButtonClickListener()
    }

    private fun setOnImageButtonClickListener() {
         val getGalleryImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val uri = result.data?.data // 선택한 이미지의 주소(상대경로)
                    val absoluteUri = getFullPathFromUri(requireContext(), uri!!)

                    GlideApp.with(requireContext())
                        .load(uri)
                        .transform(CenterCrop(), RoundedCorners(15))
                        .into(binding.imageButtonImageSelect)

                    println(uri.toString())
                    sendFile = File(absoluteUri!!)
                    println(sendFile.absolutePath.toString())
                }
            }

        binding.imageButtonImageSelect.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    mainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                getGalleryImageLauncher.launch(intent)
            }
        }
    }

    private fun setOnAnalysisButtonClickListener(){
        binding.analysis.setOnClickListener {
//            var fileName = sendFile.name
//            val requestFile = sendFile.asRequestBody("image/*".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("CellImage", fileName, requestFile)
//            CoroutineScope(Dispatchers.IO).launch {
//                val response = UserServiceRequestFactory.retrofit.sendCellImage(body)
//                if (response.isSuccessful) {
//                    Log.d("info", response.headers().toString())
//                    Log.d("info", response.body().toString())
//                }
//            }
            mainActivity.changeFragment(R.id.analysis)
        }
    }
}
