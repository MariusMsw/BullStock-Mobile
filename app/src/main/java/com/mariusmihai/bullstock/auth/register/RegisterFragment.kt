package com.mariusmihai.bullstock.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.auth.AuthenticationActivity
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.PasswordStrength
import com.mariusmihai.bullstock.core.helpers.addOnPropertyChanged
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

        viewModel.password.addOnPropertyChanged {
            updatePasswordStrengthView(it.get() ?: return@addOnPropertyChanged)
        }

    }

    private fun updatePasswordStrengthView(password: String) {

        val progressBar = binding.progressBar
        val strengthView = binding.passwordStrength

        if (TextView.VISIBLE != strengthView.visibility)
            return

        if (TextUtils.isEmpty(password)) {
            strengthView.text = ""
            progressBar.progress = 0
            return
        }

        val str = PasswordStrength.calculateStrength(password)
        strengthView.text = str.getText(context ?: return)
        strengthView.setTextColor(str.color)

        progressBar.progressDrawable.setColorFilter(
            str.color,
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        when {
            str.getText(context ?: return) == "Weak" -> {
                progressBar.progress = 25
            }
            str.getText(context ?: return) == "Medium" -> {
                progressBar.progress = 50
            }
            str.getText(context ?: return) == "Strong" -> {
                progressBar.progress = 75
            }
            else -> {
                progressBar.progress = 100
            }
        }
    }
}
