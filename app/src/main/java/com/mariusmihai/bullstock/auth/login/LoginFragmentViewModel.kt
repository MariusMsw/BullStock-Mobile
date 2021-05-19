package com.mariusmihai.bullstock.auth.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.isValidEmail
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.dto.auth.LoginForm
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import com.mariusmihai.bullstock.persistence.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragmentViewModel : ViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()

    var navigateToTrading: (() -> Unit)? = null
    var showAlert: ((String) -> Unit)? = null

    fun login() {
        val userEmail = email.get() ?: return
        val userPassword = password.get() ?: return

        if (!userEmail.isValidEmail()) return

        doLogin(userEmail, userPassword, navigateToTrading)
    }

    private fun doLogin(email: String, password: String, onSuccess: (() -> Unit)? = null) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = BullStockApiRepository.login(
                    LoginForm(email, password)
                )
                response.accessToken?.let { Preferences.saveBearerToken(it) }
                response.refreshToken?.let { Preferences.saveRefreshToken(it) }

                if (!response.accessToken.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        onSuccess?.invoke()
                    }
                }
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("An error has occurred. Please try again later.")
                }
            }
        }
}