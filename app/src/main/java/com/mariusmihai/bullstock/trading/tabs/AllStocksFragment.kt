package com.mariusmihai.bullstock.trading.tabs

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.data.dto.TradingStockDto
import com.mariusmihai.bullstock.databinding.FragmentAllStocksBinding
import com.mariusmihai.bullstock.trading.adapters.StocksAdapter


class AllStocksFragment : BaseFragment<FragmentAllStocksBinding>() {
    override val layout: Int
        get() = R.layout.fragment_all_stocks
    override val viewModel: ViewModel
        get() = AllStocksViewModel()

    private lateinit var adapter: StocksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = StocksAdapter(
            listOf(
                TradingStockDto.getPlaceholder(),
                TradingStockDto.getPlaceholder(),
                TradingStockDto.getPlaceholder(),
                TradingStockDto.getPlaceholder(),
                TradingStockDto.getPlaceholder()
            )
        )

        adapter.onStockClick = {
            findNavController().navigate(R.id.action_all_stocks_to_stockScreen)
        }
        binding.recyclerViewAllStocks.adapter = adapter
        binding.recyclerViewAllStocks.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerViewAllStocks.context,
                DividerItemDecoration.VERTICAL
            )
        )

    }
}