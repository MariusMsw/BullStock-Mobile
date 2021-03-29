package com.mariusmihai.bullstock.trading.tabs

import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.FragmentFavoritesBinding

class TopMovesFragment : BaseFragment<FragmentFavoritesBinding>() {
    override val layout: Int
        get() = R.layout.fragment_favorites
    override val viewModel: ViewModel
        get() = FavoritesViewModel()
}