package com.mariusmihai.bullstock.data.dto

import com.google.gson.annotations.SerializedName

data class AuthResponseModel(
    @SerializedName("token")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
)