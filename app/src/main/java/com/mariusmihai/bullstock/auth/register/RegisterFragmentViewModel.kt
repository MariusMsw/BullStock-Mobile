package com.mariusmihai.bullstock.auth.register

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.BaseViewModel
import com.mariusmihai.bullstock.core.helpers.isValidEmail
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.dto.auth.RegisterForm
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragmentViewModel : BaseViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val confirmPassword = ObservableField<String>()

    var showAlert: ((String) -> Unit)? = null
    var navigateToLogin: (() -> Unit)? = null

    fun register() {
        val userEmail = email.get() ?: return
        val userPassword = password.get() ?: return
        val userConfirmPassword = confirmPassword.get() ?: return

        if (userPassword != userConfirmPassword) {
            viewModelScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Passwords does not match")
                }
            }
            return
        }
        if (!userEmail.isValidEmail()) {
            viewModelScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Email is not valid")
                }
            }
            return
        }

        doRegister(userEmail, userPassword, userConfirmPassword)
    }

    private fun doRegister(email: String, password: String, confirmPassword: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                BullStockApiRepository.register(
                    RegisterForm(
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                )
                withContext(Dispatchers.Main) {
                    navigateToLogin?.invoke()
                }
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("An error has occurred. Please try again later.")
                }
            }
        }
}