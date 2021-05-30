package com.mariusmihai.bullstock.data

import com.mariusmihai.bullstock.data.dto.auth.AuthResponseModel
import com.mariusmihai.bullstock.data.dto.CashDto
import com.mariusmihai.bullstock.data.dto.auth.LoginForm
import com.mariusmihai.bullstock.data.dto.auth.RegisterForm
import com.mariusmihai.bullstock.data.dto.stocks.*
import retrofit2.http.*

interface BullStockApi {

    // ******* Auth *******
    @POST("auth/register")
    suspend fun register(@Body body: RegisterForm): AuthResponseModel

    @POST("auth/login")
    suspend fun login(@Body body: LoginForm): AuthResponseModel

    @POST("auth/logout")
    suspend fun logout(): Map<String, String>

    // ******* Cash *******
    @POST("user/deposit")
    suspend fun deposit(@Body cashDto: CashDto): Map<String, String>

    @POST("user/withdraw")
    suspend fun withdraw(@Body cashDto: CashDto): Map<String, String>

    // ******* USER *******
    @GET("user/favorite")
    suspend fun getFavoriteStocks(): MutableList<StockMostImportantDataDto>

    @GET("user/portfolio-metadata")
    suspend fun getPortfolioMetadata(): PortfolioMetadataDto

    @POST("user/favorite/{symbol}")
    suspend fun changeFavoriteStatus(@Path("symbol") symbol: String): Map<String, String>

    @GET("/user/history")
    suspend fun retrieveHistory(): MutableList<HistoryDto>

    @GET("/user/portofolio")
    suspend fun getPortfolioScreen(): MutableList<PortfolioScreenDto>

    @POST("/user/stock-buy")
    suspend fun buyStock(@Body request: TradeStockDto): Any

    @POST("/user/stock-sell")
    suspend fun sellStock(@Body request: TradeStockDto): Any

    // ******* Stocks *******
    @GET("stock")
    suspend fun getAllStocks(): MutableList<StockMostImportantDataDto>

    @POST("stock/screen")
    suspend fun getStockScreen(@Body request: StockChartRequest): StockScreenDto

    @GET("stock/winners")
    suspend fun getWinners(): List<StockMostImportantDataDto>

    @GET("stock/losers")
    suspend fun getLosers(): List<StockMostImportantDataDto>


}