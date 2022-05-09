package com.kyonggi.cellification.ui.cell

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.kyonggi.cellification.R
import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.databinding.FragmentStorageBinding
import com.kyonggi.cellification.ui.cell.adapter.CellAdapter
import com.kyonggi.cellification.ui.cell.adapter.CellLocalAdapter
import com.kyonggi.cellification.ui.cell.adapter.ItemClickListener
import com.kyonggi.cellification.ui.di.App
import com.kyonggi.cellification.ui.viewmodel.CellViewModel
import com.kyonggi.cellification.ui.viewmodel.UserViewModel
import com.kyonggi.cellification.utils.APIResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StorageFragment : Fragment() {
    private val cellViewModel: CellViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentStorageBinding
    private lateinit var recyclerViewAdapter: CellAdapter
    private lateinit var recyclerLocalViewAdapter: CellLocalAdapter
    private lateinit var mainActivity: MainActivity
    private lateinit var currentData: List<ResponseCell>
    private lateinit var currentLocalData: List<Cell>
    private val token = App.prefs.token.toString()
    private lateinit var res: Resources
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStorageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        res = resources
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeView(0)
        tabSelected()
    }

    private fun tabSelected() {
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
        when (position) {
            0 -> {
                getRemoteCellListFromUser()
            }
            1 -> {
                getLocalCellListFromUser()
            }
        }
    }

    //tab의 cell drive
    private fun initRecyclerView(list: MutableList<ResponseCell>) {
        binding.cellRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewAdapter = CellAdapter(list, res)
        binding.cellRecyclerView.adapter = recyclerViewAdapter
        setItemOnClickListener()
    }

    // tab의 save
    private fun initLocalRecyclerView(list: MutableList<Cell>) {
        binding.cellRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerLocalViewAdapter = CellLocalAdapter(list, res)
        binding.cellRecyclerView.adapter = recyclerLocalViewAdapter
        setLocalItemOnClickListener()
    }

    private fun getRemoteCellListFromUser() {
        //매개변수 바꿔야함
        cellViewModel.getCellListFromUser(token, App.prefs.userId.toString())
        cellViewModel.stateList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is APIResponse.Success -> {
                    // success code
                    currentData = it.data!!
                    initRecyclerView(it.data!!)
                }
                is APIResponse.Error -> {
                    // error code
                    Toast.makeText(requireActivity(), "로딩 실패", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getLocalCellListFromUser() {
        // 매개변수 바꿔줘야함
        cellViewModel.getCellsQueryEmail(App.prefs.userId.toString())
        cellViewModel.stateListLocal.observe(viewLifecycleOwner, Observer {
            currentLocalData = it
            initLocalRecyclerView(it)
        })
    }

    private fun setItemOnClickListener() {
        recyclerViewAdapter.setItemOnClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                // 프래그먼트 인자 바꿔야함... 개별 결과 페이지로..
                mainActivity.changeFragment(ResultRecyclerFragment(), currentData[position])
            }

            override fun onLongClick(position: Int) {
                val builder = AlertDialog.Builder(requireContext(), R.style.DeleteDialog)
                deleteDialogProcess(position, builder, 0)
            }
        })
    }

    private fun setLocalItemOnClickListener() {
        recyclerLocalViewAdapter.setItemOnClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                // 프래그먼트 인자 바꿔야함... 개별 결과 페이지로..
                mainActivity.changeFragment(ResultRecyclerFragment(), currentData[position])
            }

            override fun onLongClick(position: Int) {
                // 삭제 다이얼로그 로직?
                val builder = AlertDialog.Builder(requireContext(), R.style.DeleteDialog)
                deleteDialogProcess(position, builder, 1)
            }
        })
    }

    private fun deleteDialogProcess(position: Int, builder: AlertDialog.Builder, tabPos: Int) {
        val dialog = builder.setMessage("삭제 하시겠습니까?")
            .setPositiveButton("삭제") { dialog, _ ->
                when (tabPos) {
                    0 -> {
                        val deleteCellId = currentData[position].cellId
                        val deleteUserId = currentData[position].userId
                        cellViewModel.deleteSpecificCell(token, deleteUserId, deleteCellId)
                        dialog.dismiss()
                        recyclerViewAdapter.cellList.removeAt(position)
                        recyclerViewAdapter.notifyItemRemoved(position)
                    }
                    1 -> {
                        val cellData = currentLocalData[position]
                        val cell = Cell(
                            cellData.id,
                            cellData.liveCell,
                            cellData.deadCell,
                            cellData.imageUrl,
                            cellData.viability,
                            cellData.email
                        )
                        cellViewModel.deleteCell(cell)
                        dialog.dismiss()
                        recyclerLocalViewAdapter.cellList.removeAt(position)
                        recyclerLocalViewAdapter.notifyItemRemoved(position)
                    }
                }
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.show()
    }
}