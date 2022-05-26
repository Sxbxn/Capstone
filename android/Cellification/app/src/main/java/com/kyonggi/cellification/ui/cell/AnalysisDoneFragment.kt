package com.kyonggi.cellification.ui.cell

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kyonggi.cellification.R
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.databinding.FragmentAnalysisDoneBinding
import com.kyonggi.cellification.utils.GlideApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisDoneFragment: Fragment() {
    private lateinit var binding: FragmentAnalysisDoneBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var analysisData : ResponseCell
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisDoneBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnReselectOnClickListener()
        setOnResultButtonClickListener()
        if(arguments != null){
            val cellId = requireArguments().getString("cellId")
            val totalCell = requireArguments().getInt("totalCell")
            val liveCell = requireArguments().getInt("liveCell")
            val deadCell = requireArguments().getInt("deadCell")
            val viability = requireArguments().getDouble("viability")
            val userId = requireArguments().getString("userId")
            val url = requireArguments().getString("url")

            GlideApp.with(binding.root)
                .load(url)
                .placeholder(R.drawable.ic_baseline_settings_24)
                .error(R.drawable.ic_baseline_settings_24)
                .fallback(R.drawable.ic_baseline_settings_24)
                .into(binding.analysisCompleteImage)

            analysisData = ResponseCell(cellId!!,totalCell,liveCell,deadCell,viability,url!!,userId!!)
        }

    //        GlideApp.with(requireContext())
//            .load(R.drawable.result_hard)
//            .transform(CenterCrop(), RoundedCorners(15))
//            .into(binding.analysisCompleteImage)
    }

    private fun setOnReselectOnClickListener() {
        binding.reselect.setOnClickListener {
            mainActivity.changeFragment(AnalysisFragment())
        }
    }
    private fun setOnResultButtonClickListener() {
        binding.result.setOnClickListener {
            //cell 정보도 같이 넘겨주어야함
            mainActivity.changeFragment(ResultFragment(),analysisData)
        }
    }
}