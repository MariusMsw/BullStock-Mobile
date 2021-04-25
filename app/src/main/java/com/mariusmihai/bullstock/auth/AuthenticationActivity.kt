package com.mariusmihai.bullstock.auth

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.mariusmihai.bullstock.R

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_authentication)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_authentication_container) as NavHostFragment
        val navController = navHostFragment.navController
    }
}