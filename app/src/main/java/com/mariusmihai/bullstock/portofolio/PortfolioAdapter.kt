package com.mariusmihai.bullstock.portofolio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.data.dto.stocks.PortfolioScreenDto
import com.mariusmihai.bullstock.databinding.ItemPortfolioBinding

class PortfolioAdapter(val items: MutableList<PortfolioScreenDto>) :
    RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    var onStockClick: ((PortfolioScreenDto) -> Unit)? = null

    inner class PortfolioViewHolder(private val binding: ItemPortfolioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        public fun bind(model: PortfolioScreenDto) {
            binding.model = model
            binding.portofolioConstraintLayout.setOnClickListener {
                onStockClick?.invoke(model)
            }
            if (model.profit < 0) {
                binding.portofolioConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.portofolioConstraintLayout.context,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                binding.portofolioConstraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.portofolioConstraintLayout.context,
                        android.R.color.holo_green_light
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        return PortfolioViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_portfolio, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}