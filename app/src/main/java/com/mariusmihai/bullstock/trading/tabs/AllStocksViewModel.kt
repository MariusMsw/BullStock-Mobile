package com.mariusmihai.bullstock.trading.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.dto.stocks.StockMostImportantDataDto
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllStocksViewModel : ViewModel() {

    var showAlert: ((String) -> Unit)? = null
    val stockMostImportantData = MutableLiveData<MutableList<StockMostImportantDataDto>>()

    fun retrieveAllStocks() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                stockMostImportantData.postValue(BullStockApiRepository.getAllStocks())
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Could not retrieve all stocks")
                }
            }
        }
}