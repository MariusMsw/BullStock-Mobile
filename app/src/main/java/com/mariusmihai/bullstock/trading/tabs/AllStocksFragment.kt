package com.mariusmihai.bullstock.trading.tabs

import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.FragmentAllStocksBinding

class AllStocksFragment : BaseFragment<FragmentAllStocksBinding>() {
    override val layout: Int
        get() = R.layout.fragment_all_stocks
    override val viewModel: ViewModel
        get() = AllStocksViewModel()
}