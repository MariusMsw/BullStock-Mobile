package com.mariusmihai.bullstock.trading.tabs

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.FragmentAllStocksBinding
import com.mariusmihai.bullstock.trading.adapters.StocksAdapter
import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AllStocksFragment : BaseFragment<FragmentAllStocksBinding>() {
    override val layout: Int
        get() = R.layout.fragment_all_stocks

    override val viewModel: AllStocksViewModel by viewModels()

    private lateinit var adapter: StocksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = StocksAdapter(
            mutableListOf()
        )

        lifecycleScope.launch {
            viewModel.retrieveAllStocks()
            Observable.interval(5000, TimeUnit.MILLISECONDS)
                .subscribe {
                    viewModel.retrieveAllStocks()
                }
        }

        viewModel.showAlert = { message ->
            context?.showAlertDialog(
                title = "",
                message = message,
                positiveButtonText = "Ok"
            )
        }

        viewModel.stockMostImportantData.observe(viewLifecycleOwner, {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        })

        adapter.onStockClick = {
            findNavController().navigate(
                R.id.action_all_stocks_to_stockScreen,
                bundleOf("stock_details" to it)
            )
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