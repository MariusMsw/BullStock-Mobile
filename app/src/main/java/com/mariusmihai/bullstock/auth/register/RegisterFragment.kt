package com.mariusmihai.bullstock.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.auth.AuthenticationActivity
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.RegisterScreenBinding

class RegisterFragment : BaseFragment<RegisterScreenBinding>() {

    override val layout: Int
        get() = R.layout.register_screen

    override val viewModel: RegisterFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvAlreadyHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        viewModel.showAlert = { message ->
            context?.showAlertDialog(
                title = "",
                message = message,
                positiveButtonText = "Ok"
            )
        }

        viewModel.navigateToLogin = {
            startActivity(Intent(activity, AuthenticationActivity::class.java))
            activity?.finish()
        }
    }
}
