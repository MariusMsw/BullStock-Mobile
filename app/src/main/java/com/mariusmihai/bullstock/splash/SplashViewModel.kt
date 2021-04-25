package com.mariusmihai.bullstock.splash

import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.persistence.Preferences

class SplashViewModel(private val preferences: Preferences) : ViewModel() {

    lateinit var navigateToLogin: () -> Unit

    lateinit var navigateToTrading: () -> Unit

    fun navigateBasedOnAuth() {
        when (preferences.getUserId() != -1 && !preferences.getBearerToken().isNullOrEmpty()) {
            true -> navigateToTrading.invoke()
            false -> navigateToLogin.invoke()
        }
    }
}