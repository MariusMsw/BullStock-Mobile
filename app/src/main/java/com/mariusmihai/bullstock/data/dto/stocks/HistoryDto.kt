package com.mariusmihai.bullstock.data.dto.stocks

import com.google.gson.annotations.SerializedName
import com.mariusmihai.bullstock.core.helpers.TransactionType

class HistoryDto(
    val id: Int,
    val symbol: String,
    @SerializedName(value = "type")
    val type: TransactionType,
    val profit: Double,
    val volume: Int,
    val openDate: Long,
    val openPrice: Double,
    val transactionId: Int,
    val closeDate: Long,
    val closePrice: Double
)