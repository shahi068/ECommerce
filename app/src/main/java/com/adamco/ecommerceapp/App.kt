package com.adamco.ecommerceapp

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
    }
}