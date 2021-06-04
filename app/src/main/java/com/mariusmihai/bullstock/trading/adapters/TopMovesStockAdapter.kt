package com.mariusmihai.bullstock.trading.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.data.dto.stocks.StockMostImportantDataDto
import com.mariusmihai.bullstock.databinding.ItemLosersBinding
import com.mariusmihai.bullstock.databinding.ItemStocksBinding
import com.mariusmihai.bullstock.databinding.ItemWinnersBinding

enum class TopMovesItemType {
    WINNER,
    LOSER
}

sealed class TopMovesData(val type: TopMovesItemType)

class WinnerStockData(val model: StockMostImportantDataDto) : TopMovesData(TopMovesItemType.WINNER)
class LoserStockData(val model: StockMostImportantDataDto) : TopMovesData(TopMovesItemType.LOSER)

class TopMovesStockAdapter(val items: MutableList<TopMovesData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onStockClick: ((StockMostImportantDataDto) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TopMovesItemType.WINNER.ordinal -> WinnerStockViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_winners, parent, false
                )
            )
            else -> LosersStockViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_losers, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WinnerStockViewHolder -> (items[position] as? WinnerStockData)?.let {
                holder.bind(it)
            }
            is LosersStockViewHolder -> (items[position] as? LoserStockData)?.let {
                holder.bind(it)
            }
        }
    }

    override fun getItemCount(): Int = items.size


    inner class WinnerStockViewHolder(private val binding: ItemWinnersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        public fun bind(model: WinnerStockData) {
            binding.model = model.model
            binding.winnersConstraintLayout.setOnClickListener {
                onStockClick?.invoke(model.model)
            }
            if (model.model.priceChangeLastDay < 0) {
                binding.winnersConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.winnersConstraintLayout.context,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.winnersConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.winnersConstraintLayout.context,
                        android.R.color.holo_green_light
                    )
                )
            }
        }
    }

    inner class LosersStockViewHolder(private val binding: ItemLosersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        public fun bind(model: LoserStockData) {
            binding.model = model.model
            binding.losersConstraintLayout.setOnClickListener {
                onStockClick?.invoke(model.model)
            }
            binding.losersConstraintLayout.setOnClickListener {
                onStockClick?.invoke(model.model)
            }
            if (model.model.priceChangeLastDay < 0) {
                binding.losersConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.losersConstraintLayout.context,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.losersConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.losersConstraintLayout.context,
                        android.R.color.holo_green_light
                    )
                )
            }
        }
    }
}