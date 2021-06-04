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
import com.mariusmihai.bullstock.databinding.FragmentFavoritesBinding
import com.mariusmihai.bullstock.trading.adapters.StocksAdapter
import io.reactivex.Observable
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {
    override val layout: Int
        get() = R.layout.fragment_favorites

    override val viewModel: FavoritesViewModel by viewModels()

    private lateinit var adapter: StocksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = StocksAdapter(
            mutableListOf()
        )
        lifecycleScope.launch {
            viewModel.retrieveFavoriteStocks()
            Observable.interval(5000, TimeUnit.MILLISECONDS)
                .subscribe {
                    viewModel.retrieveFavoriteStocks()
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
                R.id.action_favorite_stocks_to_stockScreen,
                bundleOf("stock_details" to it)
            )
        }
        binding.recyclerViewFavoriteStocks.adapter = adapter

    }
}