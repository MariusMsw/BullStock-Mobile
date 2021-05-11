package com.mariusmihai.bullstock.core.network

import com.mariusmihai.bullstock.persistence.Preferences
import okhttp3.Interceptor

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain) =
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${Preferences.getBearerToken()}")
                .build()
        )
}