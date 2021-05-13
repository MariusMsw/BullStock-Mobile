package com.mariusmihai.bullstock.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.logError
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import com.mariusmihai.bullstock.persistence.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragmentViewModel : ViewModel() {

    var showAlert: ((String) -> Unit)? = null
    var navigateToLogin: (() -> Unit)? = null

    fun doLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                BullStockApiRepository.logout()
                Preferences.removeSession()
                withContext(Dispatchers.Main) {
                    navigateToLogin?.invoke()
                }
            } catch (e: Exception) {
                e.message?.logError()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("An error has occurred. Please try again later.")
                }
            }
        }
    }
}