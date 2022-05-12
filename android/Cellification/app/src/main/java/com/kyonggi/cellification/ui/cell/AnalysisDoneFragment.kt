package com.kyonggi.cellification.ui.cell

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kyonggi.cellification.R
import com.kyonggi.cellification.databinding.FragmentAnalysisDoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisDoneFragment: Fragment() {
    private lateinit var binding: FragmentAnalysisDoneBinding
    private lateinit var mainActivity: MainActivity
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
    }

    private fun setOnReselectOnClickListener() {
        binding.reselect.setOnClickListener {
            mainActivity.changeFragment(AnalysisFragment())
        }
    }
    private fun setOnResultButtonClickListener() {
        binding.result.setOnClickListener {
            //cell 정보도 같이 넘겨주어야함
            mainActivity.changeFragment(ResultFragment())
        }
    }
}