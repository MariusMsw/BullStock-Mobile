package com.mariusmihai.bullstock.data.dto.stocks

data class PortfolioScreenDto(
    val symbol: String,
    val sharesOwned: Int,
    val profit: Double,
    val yield: Double
)