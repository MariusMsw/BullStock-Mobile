package com.mariusmihai.bullstock.stock

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class StockViewModel : ViewModel() {

    val stockVolume = ObservableField<String>()
    val orderValue = ObservableField<String>()
    val stockPrice = ObservableField<String>()
    val sharesOwned = ObservableField<String>()

    fun buy() {

    }

    fun sell() {

    }
}