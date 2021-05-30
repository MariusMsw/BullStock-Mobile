package com.mariusmihai.bullstock.stock

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.StockChartPeriod
import com.mariusmihai.bullstock.core.helpers.addOnPropertyChanged
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.core.helpers.round
import com.mariusmihai.bullstock.data.dto.stocks.StockChartRequest
import com.mariusmihai.bullstock.data.dto.stocks.StockChartResponse
import com.mariusmihai.bullstock.data.dto.stocks.StockMostImportantDataDto
import com.mariusmihai.bullstock.data.dto.stocks.TradeStockDto
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StockViewModel : ViewModel() {

    val orderValue = ObservableField<String>()
    val stockVolume = ObservableField<String>().apply {
        addOnPropertyChanged {
            if (!it.get().isNullOrEmpty()) {
                var value =
                    stockPrice.get()?.toDouble()?.let { it1 -> it.get()?.toInt()?.times(it1) }
                value = value?.round(2)
                orderValue.set(value.toString())
            } else {
                orderValue.set("")
            }
        }
    }

    val stockPrice = ObservableField<String>()
    val sharesOwned = ObservableField<String>()
    val stockName = ObservableField<String>()
    val isFavorite = ObservableField<Boolean>()
    val chartData = MutableLiveData<MutableList<StockChartResponse>>()

    lateinit var stock: StockMostImportantDataDto

    var showAlert: ((String) -> Unit)? = null

    lateinit var navigateToTrading: () -> Unit

    fun retrieveStockData() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = StockChartRequest(stock.symbol, StockChartPeriod.ONE_MONTH)

                val stockScreenDto = BullStockApiRepository.getStockScreen(request)
                stockPrice.set(stockScreenDto.sharePrice.toString())
                sharesOwned.set(stockScreenDto.sharesOwned.toString())
                stockName.set(stockScreenDto.stockName)
                isFavorite.set(stockScreenDto.favorite)
                chartData.postValue(stockScreenDto.data)

            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Could not retrieve stock data!")
                }
            }
        }

    fun changeFavoriteStatus() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                BullStockApiRepository.changeFavoriteStatus(stock.symbol)
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Could not change favorite status!")
                }
            }
        }
    }

    fun buy() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!stockVolume.get().isNullOrEmpty()) {
                    val request = TradeStockDto(stock.symbol, stockVolume.get()?.toInt()!!)
                    BullStockApiRepository.buyStock(request)
                }
                sharesOwned.set(
                    (sharesOwned.get()?.toInt()?.plus(stockVolume.get()!!.toInt())).toString()
                )
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Stocks added!")
                }
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Not enough funds!")
                }
            }
        }
    }

    fun sell() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!stockVolume.get().isNullOrEmpty()) {
                    val request = TradeStockDto(stock.symbol, stockVolume.get()?.toInt()!!)
                    BullStockApiRepository.sellStock(request)
                }
                sharesOwned.set(
                    (sharesOwned.get()?.toInt()?.minus(stockVolume.get()!!.toInt())).toString()
                )
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Stocks sold!")
                }
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Too many stocks!")
                }
            }
        }
    }

    fun back() {
        navigateToTrading.invoke()
    }
}