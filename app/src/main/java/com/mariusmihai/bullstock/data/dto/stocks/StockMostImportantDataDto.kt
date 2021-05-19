package com.mariusmihai.bullstock.data.dto.stocks

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockMostImportantDataDto(
    val id: Int,
    val name: String,
    val symbol: String,
    val bid: Double,
    val ask: Double,
    val priceChangeLastDay: Double,
    val favorite: Boolean,
    val sharesOwned: Int
) : Parcelable {
    companion object {
        fun getPlaceholder(): StockMostImportantDataDto {
            return StockMostImportantDataDto(
                symbol = "AAPL",
                priceChangeLastDay = 3.21,
                bid = 147.55,
                ask = 147.58,
                sharesOwned = 5,
                name = "Apple",
                favorite = true,
                id = 1
            )
        }
    }
}