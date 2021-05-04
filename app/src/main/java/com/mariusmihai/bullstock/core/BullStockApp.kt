package com.mariusmihai.bullstock.core

import android.app.Application

class BullStockApp : Application(){

    companion object {
        var instance: BullStockApp? = null
    }

    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }
    }
}