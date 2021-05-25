package com.mariusmihai.bullstock.portofolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.dto.stocks.PortfolioScreenDto
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PortofolioFragmentViewModel : ViewModel() {

    var showAlert: ((String) -> Unit)? = null
    val portfolioScreenDto = MutableLiveData<MutableList<PortfolioScreenDto>>()

    fun retrievePortfolioScreen() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                portfolioScreenDto.postValue(BullStockApiRepository.getPortfolioScreen())
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("An error has occurred. Please try again later.")
                }
            }
        }
}