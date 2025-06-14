package com.shrimpdevs.digitalassistant.screens.event

// Android SDK

// Jetpack Compose UI
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

// Jetpack Compose Material & Material3

// Jetpack Compose Foundation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text

// Jetpack Compose Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf // Usar este en lugar de mutableIntStateOf si el tipo es genérico
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

// Bibliotecas de Terceros (Firebase)
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shrimpdevs.digitalassistant.R
import com.shrimpdevs.digitalassistant.models.Event

// Importaciones del Proyecto
import com.shrimpdevs.digitalassistant.ui.theme.Black
import com.shrimpdevs.digitalassistant.ui.theme.DarkBlue

import androidx.compose.material3.Icon
import androidx.compose.ui.draw.shadow

@Composable
fun EventScreen(db: FirebaseFirestore, navigateToCreateEvent: () -> Unit) {
    val db = Firebase.firestore

    val title by remember { mutableStateOf("") }
    val description by remember { mutableStateOf("") }
    val eventDate by remember { mutableStateOf(Timestamp.now()) }
    val location by remember { mutableStateOf("") }
    val alarm by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, Black)))
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        //Aquie se muestra eventos

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 32.dp) // Usa el padding original
                .shadow(10.dp, shape = RoundedCornerShape(15.dp))
                .background(Brush.verticalGradient(listOf(DarkBlue, Black))), // Aplica la sombra y el shape
            onClick = {
                navigateToCreateEvent()
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.create_icon),
                contentDescription = "Logo de Google",
                modifier = Modifier
                    .size(45.dp)
                    .padding(12.dp),
            )
        }
    }
}

//


@Composable
fun TargetEvent(

    actualContent: @Composable () -> Unit // Renombrado para claridad
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, Black)))
            .padding(horizontal = 35.dp), // Aplicar padding aquí al Column exterior
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Espacio para el contenido que viene de fuera
        Box(modifier = Modifier.weight(1f)) { // Un Box para que actualContent ocupe el espacio
            actualContent()
        }

        // El texto "Eventos" como parte fija de TargetEvent en la parte inferior
        Text(
            text = "Eventos",
            color = DarkBlue, // Considera un color que contraste con el fondo oscuro
            modifier = Modifier.padding(16.dp)
        )
    }
}
