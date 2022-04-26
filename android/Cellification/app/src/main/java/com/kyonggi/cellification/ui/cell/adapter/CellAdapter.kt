package com.kyonggi.cellification.ui.cell.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kyonggi.cellification.R
import com.kyonggi.cellification.data.model.cell.ResponseCell
import com.kyonggi.cellification.data.model.cell.ResponseSpecificUserCell
import com.kyonggi.cellification.databinding.ItemListBinding
import com.kyonggi.cellification.utils.APIResponse
import com.kyonggi.cellification.utils.GlideApp

class CellAdapter(
    private var cellList: List<ResponseCell>
) : RecyclerView.Adapter<CellAdapter.CellViewHolder>() {

    private lateinit var myItemClickListener: ItemClickListener
    fun setItemOnClickListener(listener:ItemClickListener){
        myItemClickListener = listener
    }
    //recyclerView 에 넘겨주기 위한 리스트
    fun setCellList(listData: APIResponse<List<ResponseCell>>) {
        this.cellList = listData.data!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.bind(cellList.get(position))
    }

    override fun getItemCount(): Int {
        return cellList.size
    }

    inner class CellViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init{
            binding.root.setOnClickListener{
                myItemClickListener.onItemClick(adapterPosition)
            }
        }

        private val cellCnt = binding.cellCountText
        private val cellViability = binding.cellViabilityText
        private val cellImage = binding.cellImage

        @SuppressLint("SetTextI18n")
        fun bind(cell: ResponseCell) {
            // cell 데이터와 바인드
            cellCnt.text = "Total: " + (cell.liveCell + cell.deadCell).toString()
            cellViability.text = "Viability:" + cell.viability.toString() + "%"
            GlideApp.with(cellImage)
                .load("any_url")
                .error(R.drawable.ic_baseline_settings_24)
                .fallback(R.drawable.ic_baseline_settings_24)
                .into(cellImage)
        }

    }


}