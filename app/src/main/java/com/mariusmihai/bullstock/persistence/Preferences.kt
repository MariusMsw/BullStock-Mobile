package com.mariusmihai.bullstock.persistence

import android.content.Context
import android.content.SharedPreferences
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.helpers.AppConstants

class Preferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    fun getBearerToken() =
        preferences.getString(AppConstants.SharedPrefKeys.ARG_BEARER_TOKEN, null);

    fun saveBearerToken(token: String) = with(preferences.edit()) {
        putString(AppConstants.SharedPrefKeys.ARG_BEARER_TOKEN, token)
        apply()
    }

    fun getUserId() =
        preferences.getInt(AppConstants.SharedPrefKeys.ARG_USER_ID, -1);

    fun saveUserId(userId: Int) = with(preferences.edit()) {
        putInt(AppConstants.SharedPrefKeys.ARG_USER_ID, userId)
        apply()
    }

    fun removeSession() = with(preferences.edit()) {
        remove(AppConstants.SharedPrefKeys.ARG_BEARER_TOKEN)
        remove(AppConstants.SharedPrefKeys.ARG_USER_ID)
        apply()
    }
}