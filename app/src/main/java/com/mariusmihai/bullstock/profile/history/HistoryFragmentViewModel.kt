package com.mariusmihai.bullstock.profile.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.data.dto.stocks.HistoryDto
import com.mariusmihai.bullstock.data.repository.BullStockApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragmentViewModel : ViewModel() {

    var showAlert: ((String) -> Unit)? = null
    val historyDto = MutableLiveData<MutableList<HistoryDto>>()

    lateinit var back: () -> Unit

    fun retrieveHistory() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = BullStockApiRepository.retrieveHistory()
                historyDto.postValue(response)
            } catch (e: Exception) {
                e.message?.printMessage()
                withContext(Dispatchers.Main) {
                    showAlert?.invoke("Could not retrieve history!")
                }
            }
        }

    fun back() {
        back.invoke()
    }
}