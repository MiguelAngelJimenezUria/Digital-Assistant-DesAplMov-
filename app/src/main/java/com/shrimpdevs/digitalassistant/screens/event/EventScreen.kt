package com.shrimpdevs.digitalassistant.screens.event

// Android SDK

// Jetpack Compose UI
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

// Jetpack Compose Material & Material3

// Jetpack Compose Foundation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import com.shrimpdevs.digitalassistant.service.db

@Composable
fun EventScreen(
    db: FirebaseFirestore,
    navigateToCreateEvent: () -> Unit,
    onEventClick: (Event) -> Unit  // Añadir este parámetro
) {
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }

    LaunchedEffect(Unit) {
        db.collection("events")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("EventScreen", "Error al obtener eventos", e)
                    return@addSnapshotListener
                }

                val eventList = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Event::class.java)
                } ?: emptyList()
                events = eventList
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, Black)))
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(45.dp))
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(events) { event ->
                EventCard(
                    event = event,
                    onEventClick = onEventClick  // Pasar la función al EventCard
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .shadow(10.dp),
            onClick = { navigateToCreateEvent() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.create_icon),
                contentDescription = "Crear evento",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Crear Evento")
        }
        Spacer(modifier = Modifier.height(45.dp))
    }
}

@Composable
fun EventCard(event: Event, onEventClick: (Event) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onEventClick(event) }
                ) {
                    Text(
                        text = event.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = event.description)
                    Text(text = "Ubicación: ${event.location}")
                    Text(text = "Alarma: ${if (event.alarm) "Activada" else "Desactivada"}")
                }

                IconButton(
                    onClick = { deleteEvent(db, event.title) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Eliminar",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
private fun deleteEvent(db: FirebaseFirestore, title: String) {
    db.collection("events")
        .whereEqualTo("title", title)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.delete()
                    .addOnFailureListener { e ->
                        Log.e("EventScreen", "Error al eliminar evento", e)
                    }
            }
        }
        .addOnFailureListener { e ->
            Log.e("EventScreen", "Error al buscar evento", e)
        }
}