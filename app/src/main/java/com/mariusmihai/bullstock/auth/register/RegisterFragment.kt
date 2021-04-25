package com.mariusmihai.bullstock.auth.register

import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.RegisterScreenBinding

class RegisterFragment : BaseFragment<RegisterScreenBinding>() {

    override val layout: Int
        get() = R.layout.register_screen
    override val viewModel: ViewModel
        get() = RegisterFragmentViewModel()
}
