package com.mariusmihai.bullstock

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.dto.stocks.PortfolioMetadataDto
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityMainViewModel : ViewModel() {

    var showAlert: ((String) -> Unit)? = null

    val portfolioValue = ObservableField<String>()
    val balance = ObservableField<String>()

    fun retrievePortfolioMetadata() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: PortfolioMetadataDto = BullStockApiRepository.getPortfolioMetadata()
                portfolioValue.set(response.portfolioValue.toString())
                balance.set(response.balance.toString())
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Could not retrieve portfolio value!")
                }
            }
        }
}