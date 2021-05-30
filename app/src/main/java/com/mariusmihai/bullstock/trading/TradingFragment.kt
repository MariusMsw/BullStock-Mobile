package com.mariusmihai.bullstock.trading

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.FragmentTradingBinding
import com.mariusmihai.bullstock.trading.adapters.TradingAdapter
import com.mariusmihai.bullstock.trading.tabs.AllStocksFragment
import com.mariusmihai.bullstock.trading.tabs.FavoritesFragment
import com.mariusmihai.bullstock.trading.tabs.TopMovesFragment

class TradingFragment : BaseFragment<FragmentTradingBinding>() {

    private lateinit var demoCollectionAdapter: TradingAdapter

    override val layout: Int
        get() = R.layout.fragment_trading
    override val viewModel: ViewModel
        get() = TradingFragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionAdapter = TradingAdapter(
            this,
            listOf(FavoritesFragment(), TopMovesFragment(), AllStocksFragment())
        )
        binding.viewPager.adapter = demoCollectionAdapter

        TabLayoutMediator(binding.tabLayoutGroup, binding.viewPager) { tab, position ->
            val titles = listOf(
                getString(R.string.favorites),
                getString(R.string.top_moves),
                getString(R.string.all_stocks)
            )
            tab.text = titles[position]
        }.attach()
    }
}