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
import com.google.android.material.tabs.TabLayout
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.databinding.FragmentStorageBinding
import com.kyonggi.cellification.ui.cell.adapter.CellAdapter
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.viewmodel.CellViewModel
import com.kyonggi.cellification.utils.APIResponse
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
                        getCellListFromUser()
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

    private fun initRecyclerView(list:List<ResponseCell>) {
        binding.cellRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewAdapter = CellAdapter(list)
        binding.cellRecyclerView.adapter = recyclerViewAdapter
    }

    private fun initViewModel() {
        cellViewModel.stateList.observe(viewLifecycleOwner, Observer {
            if(it != null){
                recyclerViewAdapter.setCellList(it)
                recyclerViewAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(requireContext(),"error in getting data",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getCellListFromUser(){
        //매개변수 바꿔야함
        cellViewModel.getCellListFromUser(App.prefs.userId.toString())
        cellViewModel.stateList.observe(requireActivity(), Observer {
            when (it) {
                is APIResponse.Success -> {
                    // success code
                    initRecyclerView(it.data!!)
                    Toast.makeText(requireActivity(), "생성 성공", Toast.LENGTH_SHORT).show()
                }
                is APIResponse.Error -> {
                    // error code
                    Toast.makeText(requireActivity(), "생성 실패", Toast.LENGTH_SHORT).show()
                }
                is APIResponse.Loading -> {
                    // loading code
                }
            }
        })
    }

}