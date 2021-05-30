package com.mariusmihai.bullstock.trading.tabs

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.FragmentFavoritesBinding
import com.mariusmihai.bullstock.databinding.FragmentTopMovesBinding
import com.mariusmihai.bullstock.trading.adapters.StocksAdapter
import com.mariusmihai.bullstock.trading.adapters.TopMovesStockAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TopMovesFragment : BaseFragment<FragmentTopMovesBinding>() {
    override val layout: Int
        get() = R.layout.fragment_top_moves

    override val viewModel: TopMovesViewModel by viewModels()

    private lateinit var adapter: TopMovesStockAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TopMovesStockAdapter(
            mutableListOf()
        )

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
                R.id.action_top_moves_to_stockScreen,
                bundleOf("stock_details" to it)
            )
        }
        binding.recyclerViewTopMoves.adapter = adapter
        binding.recyclerViewTopMoves.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerViewTopMoves.context,
                DividerItemDecoration.VERTICAL
            )
        )

    }
}