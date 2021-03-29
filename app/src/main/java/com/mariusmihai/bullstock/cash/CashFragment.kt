package com.mariusmihai.bullstock.cash

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.FragmentCashBinding

class CashFragment : BaseFragment<FragmentCashBinding>() {

    override val layout: Int
        get() = R.layout.fragment_cash
    override val viewModel: ViewModel
        get() = CashFragmentViewModel()

}