package com.shrimpdevs.digitalassistant.service

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.shrimpdevs.digitalassistant.widget.EventWidget

class AuthStateListener(private val context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        // Actualizar widgets cuando cambie el estado de autenticación
        EventWidget.Companion.updateAllWidgets(context)
    }

    fun startListening() {
        auth.addAuthStateListener(authListener)
    }

    fun stopListening() {
        auth.removeAuthStateListener(authListener)
    }
}