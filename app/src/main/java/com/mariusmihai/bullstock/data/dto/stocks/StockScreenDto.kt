package com.mariusmihai.bullstock.data.dto.stocks

data class StockScreenDto(
    val data: List<StockChartResponse>,
    val favorite: Boolean,
    val stockName: String,
    val sharesOwned: Int,
    val sharePrice: Double
)