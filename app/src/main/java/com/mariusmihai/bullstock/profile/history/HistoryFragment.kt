package com.mariusmihai.bullstock.profile.history

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.FragmentHistoryBinding
import com.mariusmihai.bullstock.trading.adapters.StocksAdapter
import com.mariusmihai.bullstock.trading.tabs.AllStocksViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    override val layout: Int
        get() = R.layout.fragment_history

    override val viewModel: HistoryFragmentViewModel by viewModels()

    private lateinit var adapter: HistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryAdapter(
            mutableListOf()
        )

        viewModel.retrieveHistory()

        viewModel.historyDto.observe(viewLifecycleOwner, {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        })

        binding.recyclerViewHistory.adapter = adapter
        binding.recyclerViewHistory.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerViewHistory.context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.back = {
            findNavController().popBackStack()
        }
    }
}
