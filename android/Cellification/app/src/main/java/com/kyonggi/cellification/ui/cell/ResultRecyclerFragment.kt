package com.kyonggi.cellification.ui.cell

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kyonggi.cellification.R
import com.kyonggi.cellification.databinding.FragmentResultBinding
import com.kyonggi.cellification.databinding.FragmentResultRecyclerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultRecyclerFragment : Fragment() {

    private lateinit var binding: FragmentResultRecyclerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultRecyclerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null){
            val totalCell = requireArguments().getInt("totalCell")
            val liveCell = requireArguments().getInt("liveCell")
            val deadCell = requireArguments().getInt("deadCell")
            val viability = requireArguments().getDouble("viability")

            initView(totalCell,liveCell,deadCell,viability)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView(total: Int, live: Int, dead: Int, Viability:Double) {
        with(binding) {
            totalCountText.text =
                "Total Count: $total"
            viability.text = "$Viability%"
            liveCell.text = live.toString()
            dieCell.text = dead.toString()
        }
    }
}