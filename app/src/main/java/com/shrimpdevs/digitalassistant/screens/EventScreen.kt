package com.shrimpdevs.digitalassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.shrimpdevs.digitalassistant.ui.theme.Black
import com.shrimpdevs.digitalassistant.ui.theme.BlueDark
import com.shrimpdevs.digitalassistant.ui.theme.DarkBlue
import com.shrimpdevs.digitalassistant.ui.theme.LightBlue
import com.shrimpdevs.digitalassistant.ui.theme.White

import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen() {

    val db = Firebase.firestore

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = DarkBlue,  // Color principal azul oscuro
                    titleContentColor = White,  // Color blanco para el texto
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BlueDark,  // Color de fondo azul oscuro
                contentColor = White,  // Color blanco para el contenido
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                    color = White  // Color blanco para el texto
                )
            }
        },
        containerColor = Black,  // Color de fondo negro para el Scaffold
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = LightBlue  // Color secundario azul más claro
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    tint = White  // Color blanco para el icono
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = """Hola soy un texto ajsaadasd""".trimIndent(),
                color = White  // Color blanco para el texto
            )
        }
    }
}

@Preview(showBackground = true) // Muestra un fondo para la previsualización
@Composable
fun EventScreenPreview() {
    EventScreen()
}