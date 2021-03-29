package com.mariusmihai.bullstock.profile

import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val layout: Int
        get() = R.layout.fragment_profile
    override val viewModel: ViewModel
        get() = ProfileFragmentViewModel()
}