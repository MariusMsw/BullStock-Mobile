package com.mariusmihai.bullstock.data

import com.mariusmihai.bullstock.data.dto.auth.AuthResponseModel
import com.mariusmihai.bullstock.data.dto.CashDto
import com.mariusmihai.bullstock.data.dto.stocks.StockMostImportantDataDto
import com.mariusmihai.bullstock.data.dto.auth.LoginForm
import com.mariusmihai.bullstock.data.dto.auth.RegisterForm
import com.mariusmihai.bullstock.data.dto.stocks.PortfolioMetadataDto
import com.mariusmihai.bullstock.data.dto.stocks.StockChartRequest
import com.mariusmihai.bullstock.data.dto.stocks.StockScreenDto
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

    // ******* Stocks *******
    @GET("stock")
    suspend fun getAllStocks(): MutableList<StockMostImportantDataDto>

    @POST("stock/screen")
    suspend fun getStockScreen(@Body request: StockChartRequest): StockScreenDto

    @GET("user/portfolio-metadata")
    suspend fun getPortfolioMetadata(): PortfolioMetadataDto
}