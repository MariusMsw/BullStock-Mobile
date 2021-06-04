package com.mariusmihai.bullstock.profile.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.helpers.TransactionType
import com.mariusmihai.bullstock.data.dto.stocks.HistoryDto
import com.mariusmihai.bullstock.databinding.ItemHistoryBinding

class HistoryAdapter(val items: MutableList<HistoryDto>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        public fun bind(model: HistoryDto) {
            binding.model = model
            if (model.type == TransactionType.SELL) {
                binding.historyConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.historyConstraintLayout.context,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.historyConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.historyConstraintLayout.context,
                        android.R.color.holo_green_light
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        return HistoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_history, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}