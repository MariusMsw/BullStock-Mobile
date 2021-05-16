package com.mariusmihai.bullstock.data

import com.mariusmihai.bullstock.data.dto.AuthResponseModel
import com.mariusmihai.bullstock.data.dto.CashDto
import com.mariusmihai.bullstock.data.dto.LoginForm
import com.mariusmihai.bullstock.data.dto.RegisterForm
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
}