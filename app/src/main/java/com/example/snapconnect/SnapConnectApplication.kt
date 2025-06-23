package com.example.snapconnect

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SnapConnectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
} 