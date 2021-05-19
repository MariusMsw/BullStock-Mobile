package com.mariusmihai.bullstock.trading.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.data.dto.stocks.StockMostImportantDataDto
import com.mariusmihai.bullstock.databinding.ItemStocksBinding

class StocksAdapter(val items: MutableList<StockMostImportantDataDto>) :
    RecyclerView.Adapter<StocksAdapter.StockViewHolder>() {

    var onStockClick: ((StockMostImportantDataDto) -> Unit)? = null

    inner class StockViewHolder(private val binding: ItemStocksBinding) :
        RecyclerView.ViewHolder(binding.root) {

        public fun bind(model: StockMostImportantDataDto) {
            binding.model = model
            binding.itemStocksConstraintLayout.setOnClickListener {
                onStockClick?.invoke(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_stocks, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}