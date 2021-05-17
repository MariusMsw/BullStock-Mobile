package com.mariusmihai.bullstock.data.dto

data class TradingStockDto(
    val symbol: String,
    val priceChangeLastDay: Double,
    val bid: Double,
    val ask: Double,
    val sharesOwned: Int
) {
    companion object {
        fun getPlaceholder(): TradingStockDto {
            return TradingStockDto(
                symbol = "AAPL",
                priceChangeLastDay = 3.21,
                bid = 147.55,
                ask = 147.58,
                sharesOwned = 5
            )
        }
    }
}