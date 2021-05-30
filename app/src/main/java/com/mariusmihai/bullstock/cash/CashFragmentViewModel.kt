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
                viewModelScope.launch(Dispatchers.IO) {
                    if (value > 1000) {
                        withContext(Dispatchers.Main) {
                            showAlert?.invoke("Please deposit below 1000!")
                        }
                        depositString.set("")
                        return@launch
                    }
                    try {
                        BullStockApiRepository.deposit(
                            CashDto(
                                amount = value
                            )
                        )
                        depositString.set("")
                    } catch (e: Exception) {
                        e.message?.printMessage()
                        withContext(Dispatchers.Main) {
                            showAlert?.invoke("Deposit failed!")
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
                        withdrawString.set("")
                    } catch (e: Exception) {
                        e.message?.printMessage()
                        withdrawString.set("")
                        withContext(Dispatchers.Main) {
                            showAlert?.invoke("Not enough funds!")
                        }
                    }
                }
            }
        }
    }
}