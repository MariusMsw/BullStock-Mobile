package com.mariusmihai.bullstock.data.dto.stocks

import com.mariusmihai.bullstock.core.helpers.StockChartPeriod

data class StockChartRequest(
    val symbol: String,
    val period: StockChartPeriod
)