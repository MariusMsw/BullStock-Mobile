package com.mariusmihai.bullstock.auth.login

import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.LoginScreenBinding

class LoginFragment : BaseFragment<LoginScreenBinding>() {

    override val layout: Int
        get() = R.layout.login_screen
    override val viewModel: ViewModel
        get() = LoginFragmentViewModel()
}