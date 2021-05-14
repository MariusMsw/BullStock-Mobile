package com.mariusmihai.bullstock.splash

import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.persistence.Preferences

class SplashViewModel : ViewModel() {

    lateinit var navigateToLogin: () -> Unit

    lateinit var navigateToMain: () -> Unit

    fun navigateBasedOnAuth() {
        when (!Preferences.getBearerToken().isNullOrEmpty()) {
            true -> navigateToMain.invoke()
            false -> navigateToLogin.invoke()
        }
    }
}