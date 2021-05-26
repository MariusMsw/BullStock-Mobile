package com.mariusmihai.bullstock.portofolio

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.data.dto.stocks.StockMostImportantDataDto
import com.mariusmihai.bullstock.databinding.FragmentPortofolioBinding
import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PortofolioFragment : BaseFragment<FragmentPortofolioBinding>() {
    override val layout: Int
        get() = R.layout.fragment_portofolio

    override val viewModel: PortofolioFragmentViewModel by viewModels()

    private lateinit var adapter: PortfolioAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PortfolioAdapter(
            mutableListOf()
        )

        lifecycleScope.launch {
            viewModel.retrievePortfolioScreen()
            Observable.interval(5000, TimeUnit.MILLISECONDS)
                .subscribe {
                    viewModel.retrievePortfolioScreen()
                }
        }

        viewModel.portfolioScreenDto.observe(viewLifecycleOwner, {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        })

        adapter.onStockClick = {
            val mostImportantDataDto = StockMostImportantDataDto(
                symbol = it.symbol,
                id = 0,
                favorite = false,
                name = "",
                sharesOwned = 0,
                ask = 0.0,
                bid = 0.0,
                priceChangeLastDay = 0.0
            )

            findNavController().navigate(
                R.id.action_portfolioScreen_to_stockScreen,
                bundleOf("stock_details" to mostImportantDataDto)
            )
        }
        binding.recyclerViewPortfolio.adapter = adapter
        binding.recyclerViewPortfolio.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerViewPortfolio.context,
                DividerItemDecoration.VERTICAL
            )
        )

    }
}