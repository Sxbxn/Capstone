package com.kyonggi.cellification.ui.cell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.kyonggi.cellification.databinding.FragmentStorageBinding
import com.kyonggi.cellification.ui.cell.adapter.CellAdapter
import com.kyonggi.cellification.ui.viewmodel.CellViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StorageFragment : Fragment() {
    private val cellViewModel: CellViewModel by viewModels()
    private lateinit var binding: FragmentStorageBinding
    private lateinit var recyclerViewAdapter: CellAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabSelected()
    }
    private fun tabSelected(){
        val tabLayout = binding.tabLayout

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0->{
                        initRecyclerView()
                        initViewModel()
                    }
                    1->{}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
    private fun initRecyclerView() {
        binding.cellRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewAdapter = CellAdapter()
        binding.cellRecyclerView.adapter = recyclerViewAdapter
    }

    private fun initViewModel() {
        cellViewModel.stateSpecificUserCell.observe(viewLifecycleOwner, Observer {
            if(it != null){
                recyclerViewAdapter.setCellList(it)
                recyclerViewAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(requireContext(),"error in getting data",Toast.LENGTH_SHORT).show()
            }
        })
    }
}