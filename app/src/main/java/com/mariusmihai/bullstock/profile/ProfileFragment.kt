package com.mariusmihai.bullstock.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.mariusmihai.bullstock.MainActivity
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.auth.AuthenticationActivity
import com.mariusmihai.bullstock.auth.login.LoginFragment
import com.mariusmihai.bullstock.auth.login.LoginFragmentViewModel
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val layout: Int
        get() = R.layout.fragment_profile

    override val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
        binding.historyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_historyFragment)

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

        viewModel.navigateToHistory = {
            findNavController().navigate(R.id.action_profileFragment_to_historyFragment)
        }
    }
}