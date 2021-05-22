package com.mariusmihai.bullstock.trading.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import com.mariusmihai.bullstock.trading.adapters.LoserStockData
import com.mariusmihai.bullstock.trading.adapters.TopMovesData
import com.mariusmihai.bullstock.trading.adapters.WinnerStockData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopMovesViewModel : ViewModel() {

    var showAlert: ((String) -> Unit)? = null
    val stockMostImportantData = MutableLiveData<List<TopMovesData>>()

    fun retrieveWinners() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val winners = BullStockApiRepository.getWinners()
                stockMostImportantData.postValue(winners.map { WinnerStockData(it) })
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("An error has occurred. Please try again later.")
                }
            }
        }

    fun retrieveLosers() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val losers = BullStockApiRepository.getLosers()
                stockMostImportantData.postValue(losers.map { LoserStockData(it) })
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("An error has occurred. Please try again later.")
                }
            }
        }
}