package com.mariusmihai.bullstock.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mariusmihai.bullstock.MainActivity
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.LoginScreenBinding

class LoginFragment : BaseFragment<LoginScreenBinding>() {

    override val layout: Int
        get() = R.layout.login_screen

    override val viewModel: LoginFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.showAlert = { message ->
            context?.showAlertDialog(
                title = "",
                message = message,
                positiveButtonText = "Ok"
            )
        }

        viewModel.navigateToTrading = {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }
}