package com.verona1024.ttestapp.presentation

import android.app.Application
import android.content.Context

class TestAppApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private lateinit var instance: TestAppApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}