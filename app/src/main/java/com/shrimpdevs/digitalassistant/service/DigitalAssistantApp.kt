package com.shrimpdevs.digitalassistant.service

import android.app.Application
import com.facebook.FacebookSdk

class DigitalAssistantApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
    }
}