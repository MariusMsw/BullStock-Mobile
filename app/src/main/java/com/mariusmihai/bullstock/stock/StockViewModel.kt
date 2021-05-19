package com.mariusmihai.bullstock.stock

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.StockChartPeriod
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.dto.stocks.StockChartRequest
import com.mariusmihai.bullstock.data.dto.stocks.StockChartResponse
import com.mariusmihai.bullstock.data.dto.stocks.StockMostImportantDataDto
import com.mariusmihai.bullstock.data.dto.stocks.StockScreenDto
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StockViewModel : ViewModel() {

    val orderValue = ObservableField<String>()
    val stockVolume = ObservableField<String>()

    val stockPrice = ObservableField<String>()
    val sharesOwned = ObservableField<String>()
    val stockName = ObservableField<String>()
    val isFavorite = ObservableField<Boolean>()
    val chartData = ObservableField<List<StockChartResponse>>()

    lateinit var stock: StockMostImportantDataDto

    var showAlert: ((String) -> Unit)? = null


    lateinit var navigateToTrading: () -> Unit

    fun retrieveStockData() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = StockChartRequest(stock.symbol, StockChartPeriod.ONE_DAY)

                val stockScreenDto = BullStockApiRepository.getStockScreen(request)
                stockPrice.set(stockScreenDto.sharePrice.toString())
                sharesOwned.set(stockScreenDto.sharesOwned.toString())
                stockName.set(stockScreenDto.stockName)
                isFavorite.set(stockScreenDto.favorite)
                chartData.set(stockScreenDto.data)

            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("An error has occurred. Please try again later.")
                }
            }
        }

    fun buy() {

    }

    fun sell() {

    }

    fun back() {
        navigateToTrading.invoke()
    }
}