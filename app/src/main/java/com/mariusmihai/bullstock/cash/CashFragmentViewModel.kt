package com.mariusmihai.bullstock.cash

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.dto.CashDto
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CashFragmentViewModel : ViewModel() {

    val depositString = ObservableField<String>()
    val withdrawString = ObservableField<String>()

    var showAlert: ((String) -> Unit)? = null

    fun deposit() {
        if (!depositString.get().isNullOrEmpty()) {
            val value = depositString.get()?.toDouble()
            if (value != null) {
                if (value <= 1000) {
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            BullStockApiRepository.deposit(
                                CashDto(
                                    amount = value
                                )
                            )
                        } catch (e: Exception) {
                            e.message?.printMessage()
                            withContext(Dispatchers.Main) {
                                showAlert?.invoke("An error has occurred. Please try again later.")
                            }
                        }
                    }
                }
            }
        }
    }

    fun withdraw() {
        if (!withdrawString.get().isNullOrEmpty()) {
            val value = withdrawString.get()?.toDouble()
            //TODO banii userului
            if (value != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        BullStockApiRepository.withdraw(
                            CashDto(
                                amount = value
                            )
                        )
                    } catch (e: Exception) {
                        e.message?.printMessage()
                        withContext(Dispatchers.Main) {
                            showAlert?.invoke("An error has occurred. Please try again later.")
                        }
                    }
                }
            }
        }
    }
}