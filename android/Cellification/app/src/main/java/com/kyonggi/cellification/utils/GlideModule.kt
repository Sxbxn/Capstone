package com.kyonggi.cellification.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kyonggi.cellification.databinding.FragmentAnalysisBinding
import dagger.hilt.android.qualifiers.ApplicationContext

@GlideModule
class GlideModule() : AppGlideModule() {
}