package com.mariusmihai.bullstock.auth.register

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.BaseViewModel
import com.mariusmihai.bullstock.core.helpers.isValidEmail
import com.mariusmihai.bullstock.core.helpers.logError
import com.mariusmihai.bullstock.data.dto.RegisterForm
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import com.mariusmihai.bullstock.persistence.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragmentViewModel : BaseViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val confirmPassword = ObservableField<String>()

    var navigateToTrading: (() -> Unit)? = null
    var showAlert: ((String) -> Unit)? = null

    fun register() {
        val userEmail = email.get() ?: return
        val userPassword = password.get() ?: return
        val userConfirmPassword = confirmPassword.get() ?: return

        if (userPassword != userConfirmPassword) return
        if (!userEmail.isValidEmail()) return

        doRegister(userEmail, userPassword, userConfirmPassword)
    }

    fun doRegister(email: String, password: String, confirmPassword: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = BullStockApiRepository.register(
                    RegisterForm(
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                )
                response.accessToken?.let { Preferences.saveBearerToken(it) }
                response.refreshToken?.let { Preferences.saveRefreshToken(it) }

                if (!response.accessToken.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        navigateToTrading?.invoke()
                    }
                }

            } catch (e: Exception) {
                e.message?.logError()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("An error has occurred. Please try again later.")
                }
            }
        }
}