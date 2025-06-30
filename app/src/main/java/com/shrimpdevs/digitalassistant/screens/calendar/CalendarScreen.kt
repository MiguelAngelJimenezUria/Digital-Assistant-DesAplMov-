package com.shrimpdevs.digitalassistant.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shrimpdevs.digitalassistant.ui.theme.DarkBlue
import com.shrimpdevs.digitalassistant.ui.theme.White

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    titulo: String,
    contenido: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = titulo, color = White) },
                // Puedes personalizar más la barra aquí
                containerColor = DarkBlue
            )
        },
        containerColor = DarkBlue // Fondo del Scaffold
    ) { innerPadding ->
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(innerPadding)
        ) {
            contenido()
        }
    }
}
 */