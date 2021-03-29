package com.mariusmihai.bullstock.portofolio

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.FragmentPortofolioBinding

class PortofolioFragment : BaseFragment<FragmentPortofolioBinding>() {
    override val layout: Int
        get() = R.layout.fragment_portofolio
    override val viewModel: ViewModel
        get() = PortofolioFragmentViewModel()
}