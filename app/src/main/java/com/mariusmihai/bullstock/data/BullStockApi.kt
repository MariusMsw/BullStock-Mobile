package com.mariusmihai.bullstock.data

import com.mariusmihai.bullstock.data.dto.AuthResponseModel
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
}