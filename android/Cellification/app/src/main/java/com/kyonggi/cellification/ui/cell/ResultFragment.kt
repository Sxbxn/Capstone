package com.kyonggi.cellification.ui.cell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kyonggi.cellification.R
import com.kyonggi.cellification.databinding.FragmentResultBinding
import com.kyonggi.cellification.ui.viewmodel.CellViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private val cellViewModel: CellViewModel by viewModels()
    private lateinit var binding: FragmentResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setOnButtonClickListener()
    }

    private fun setOnButtonClickListener() {
        binding.button.setOnClickListener {
            //local db에 저장
//            cellViewModel.insertCell(cell)
        }
    }

    private fun initViewModel() {
        cellViewModel.state.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                with(binding) {
                    totalCountText.text =
                       getString(R.string.total_count, it.data!!.totalCell)
                    viability.text = getString(R.string.percent, it.data.viability)
                    liveCell.text = it.data.liveCell.toString()
                    dieCell.text = it.data.deadCell.toString()
                }
            } else {
                Toast.makeText(requireContext(), "error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}