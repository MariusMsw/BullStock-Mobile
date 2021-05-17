package com.mariusmihai.bullstock.data.repository

import com.google.gson.Gson
import com.mariusmihai.bullstock.BuildConfig
import com.mariusmihai.bullstock.core.network.AuthInterceptor
import com.mariusmihai.bullstock.data.BullStockApi
import com.mariusmihai.bullstock.data.dto.CashDto
import com.mariusmihai.bullstock.data.dto.LoginForm
import com.mariusmihai.bullstock.data.dto.RegisterForm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BullStockApiRepository {

    private val okHttpClientWithAuth =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            .addInterceptor(AuthInterceptor())
            .build()

    private val retrofitClientWithAuth = Retrofit.Builder().baseUrl("http://192.168.0.105:8080/")
        .client(okHttpClientWithAuth)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    private val okHttpClientWithoutAuth =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            .build()

    private val retrofitClientWithoutAuth = Retrofit.Builder().baseUrl("http://192.168.0.105:8080/")
        .client(okHttpClientWithoutAuth)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()


    private val bullStockApiWithoutAuth = retrofitClientWithoutAuth.create(BullStockApi::class.java)
    private val bullStockApiWithAuth = retrofitClientWithAuth.create(BullStockApi::class.java)

    suspend fun register(registerForm: RegisterForm) =
        bullStockApiWithoutAuth.register(registerForm)

    suspend fun login(loginForm: LoginForm) = bullStockApiWithoutAuth.login(loginForm)

    suspend fun logout() = bullStockApiWithAuth.logout()

    suspend fun deposit(cashDto: CashDto) = bullStockApiWithAuth.deposit(cashDto)
    suspend fun withdraw(cashDto: CashDto) = bullStockApiWithAuth.withdraw(cashDto)

}