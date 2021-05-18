package com.mariusmihai.bullstock.stock

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.StockScreenBinding

class StockFragment : BaseFragment<StockScreenBinding>() {

    override val layout: Int
        get() = R.layout.stock_screen

    override val viewModel: StockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
}