package com.kyonggi.cellification.ui.cell.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyonggi.cellification.R
import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.databinding.ItemListBinding
import com.kyonggi.cellification.utils.GlideApp

class CellLocalAdapter(
    var cellList: MutableList<Cell>,
    val res: Resources,
) : RecyclerView.Adapter<CellLocalAdapter.CellViewHolder>() {

    private lateinit var myItemClickListener: ItemClickListener
    fun setItemOnClickListener(listener: ItemClickListener) {
        myItemClickListener = listener
    }
    //recyclerView 에 넘겨주기 위한 리스트

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
        init {
            binding.root.setOnClickListener {
                myItemClickListener.onItemClick(adapterPosition)
            }
            binding.root.setOnLongClickListener {
                myItemClickListener.onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

        private val cellCnt = binding.cellCountText
        private val cellViability = binding.cellViabilityText
        private val cellImage = binding.cellImage

        @SuppressLint("SetTextI18n")
        fun bind(cell: Cell) {
            // cell 데이터와 바인드
            cellCnt.text = res.getString(R.string.total_count, (cell.liveCell + cell.deadCell))
            cellViability.text = res.getString(R.string.recycler_percent, cell.viability)
            GlideApp.with(itemView.context)
                .load(cell.imageUrl)
                .placeholder(R.drawable.ic_baseline_settings_24)
                .error(R.drawable.ic_baseline_settings_24)
                .fallback(R.drawable.ic_baseline_settings_24)
                .into(cellImage)
        }
    }
}

