package com.mariusmihai.bullstock.splash

import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.persistence.Preferences

class SplashViewModel : ViewModel() {

    lateinit var navigateToLogin: () -> Unit

    lateinit var navigateToTrading: () -> Unit

    fun navigateBasedOnAuth() {
        when (Preferences.getUserId() != -1 && !Preferences.getBearerToken().isNullOrEmpty()) {
            true -> navigateToTrading.invoke()
            false -> navigateToLogin.invoke()
        }
    }
}