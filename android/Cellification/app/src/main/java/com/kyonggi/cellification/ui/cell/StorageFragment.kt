package com.kyonggi.cellification.ui.cell

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.databinding.FragmentStorageBinding
import com.kyonggi.cellification.ui.cell.adapter.CellAdapter
import com.kyonggi.cellification.ui.cell.adapter.CellLocalAdapter
import com.kyonggi.cellification.ui.cell.adapter.ItemClickListener
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.viewmodel.CellViewModel
import com.kyonggi.cellification.utils.APIResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StorageFragment : Fragment() {
    private val cellViewModel: CellViewModel by viewModels()
    private lateinit var binding: FragmentStorageBinding
    private lateinit var recyclerViewAdapter: CellAdapter
    private lateinit var recyclerLocalViewAdapter: CellLocalAdapter
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeView(0)
        tabSelected()
    }
    private fun tabSelected(){
        val tabLayout = binding.tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = tab?.position
                changeView(pos!!)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
    private fun changeView(position: Int) {
        when(position){
            0->{
                getRemoteCellListFromUser()
            }
            1->{
                getLocalCellListFromUser()
            }
        }
    }
    //tab의 cell drive
    private fun initRecyclerView(list:List<ResponseCell>) {
        binding.cellRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewAdapter = CellAdapter(list)
        binding.cellRecyclerView.adapter = recyclerViewAdapter
        setItemOnClickListener()
    }

    // tab의 save
    private fun initLocalRecyclerView(list:List<Cell>) {
        binding.cellRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerLocalViewAdapter = CellLocalAdapter(list)
        binding.cellRecyclerView.adapter = recyclerLocalViewAdapter
        setLocalItemOnClickListener()
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

    private fun getRemoteCellListFromUser(){
        //매개변수 바꿔야함
        cellViewModel.getCellListFromUser(App.prefs.userId.toString())
        cellViewModel.stateList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is APIResponse.Success -> {
                    // success code
                    initRecyclerView(it.data!!)
                    recyclerViewAdapter.notifyDataSetChanged()
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
    private fun getLocalCellListFromUser(){
        // 매개변수 바꿔줘야함
        cellViewModel.getCellsQueryEmail(App.prefs.userId.toString())
        cellViewModel.stateListLocal.observe(viewLifecycleOwner,Observer{
            initLocalRecyclerView(it)
            while(it.iterator().hasNext()){
                Log.i("result",it.iterator().next().toString())
            }
        })
    }
    private fun setItemOnClickListener(){
        recyclerViewAdapter.setItemOnClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                // 프래그먼트 인자 바꿔야함... 개별 결과 페이지로..
                mainActivity.changeFragment(ResultFragment())
            }

            override fun onLongClick(position: Int) {
                // 삭제 다이얼로그 로직?
            }
        })
    }
    private fun setLocalItemOnClickListener(){
        recyclerLocalViewAdapter.setItemOnClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                // 프래그먼트 인자 바꿔야함... 개별 결과 페이지로..
                mainActivity.changeFragment(ResultFragment())
            }

            override fun onLongClick(position: Int) {
                // 삭제 다이얼로그 로직?
            }
        })
    }
}