package com.shrimpdevs.digitalassistant.service

import android.app.Application
import com.shrimpdevs.digitalassistant.service.AuthStateListener

class DigitalAssistantApp : Application() {
    private lateinit var authStateListener: AuthStateListener

    override fun onCreate() {
        super.onCreate()
        authStateListener = AuthStateListener(this)
        authStateListener.startListening()
    }

    override fun onTerminate() {
        super.onTerminate()
        authStateListener.stopListening()
    }
}