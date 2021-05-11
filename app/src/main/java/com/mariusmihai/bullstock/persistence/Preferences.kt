package com.mariusmihai.bullstock.persistence

import android.content.Context
import android.content.SharedPreferences
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BullStockApp
import com.mariusmihai.bullstock.core.helpers.AppConstants

object Preferences {
    private val preferences: SharedPreferences? = BullStockApp.instance?.getSharedPreferences(
        BullStockApp.instance?.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    fun getBearerToken() =
        preferences?.getString(AppConstants.SharedPrefKeys.ARG_BEARER_TOKEN, null);

    fun saveBearerToken(token: String) = with(preferences?.edit()) {
        this?.putString(AppConstants.SharedPrefKeys.ARG_BEARER_TOKEN, token)
        this?.apply()
    }

    fun getRefreshToken() =
        preferences?.getString(AppConstants.SharedPrefKeys.ARG_REFRESH_TOKEN, null);

    fun saveRefreshToken(token: String) = with(preferences?.edit()) {
        this?.putString(AppConstants.SharedPrefKeys.ARG_REFRESH_TOKEN, token)
        this?.apply()
    }

    fun removeSession() = with(preferences?.edit()) {
        this?.remove(AppConstants.SharedPrefKeys.ARG_BEARER_TOKEN)
        this?.remove(AppConstants.SharedPrefKeys.ARG_REFRESH_TOKEN)
        this?.apply()
    }
}