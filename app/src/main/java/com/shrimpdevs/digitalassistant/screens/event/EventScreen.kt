package com.shrimpdevs.digitalassistant.screens.event

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.shrimpdevs.digitalassistant.R
import com.shrimpdevs.digitalassistant.models.Event
import com.shrimpdevs.digitalassistant.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    db: FirebaseFirestore,
    navigateToCreateEvent: () -> Unit,
    navigateToInitial: () -> Unit,
    onEventClick: (Event) -> Unit,
    navigateToSettings: () -> Unit
) {
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        db.collection("events")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("EventScreen", "Error al obtener eventos", e)
                    return@addSnapshotListener
                }

                val eventList = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Event::class.java)
                }?.sortedBy { it.eventDate.toDate() } ?: emptyList()
                events = eventList
            }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Eventos", color = White) },
                navigationIcon = {
                    IconButton(onClick = navigateToInitial) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Regresar",
                            tint = White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Aquí va la navegación a ajustes */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = "Ajustes",
                            tint = White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DarkBlue
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DarkBlue,
                contentColor = White
            ) {
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_event),
                            contentDescription = "Inicio"
                        )
                    },
                    label = { Text("Inicio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = White,
                        unselectedIconColor = White.copy(alpha = 0.5f),
                        selectedTextColor = White,
                        unselectedTextColor = White.copy(alpha = 0.5f),
                        indicatorColor = DarkText
                    )
                )
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Calendario"
                        )
                    },
                    label = { Text("Calendario") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = White,
                        unselectedIconColor = White.copy(alpha = 0.5f),
                        selectedTextColor = White,
                        unselectedTextColor = White.copy(alpha = 0.5f),
                        indicatorColor = DarkText
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToCreateEvent,
                containerColor = DarkText,
                contentColor = White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.create_icon),
                    contentDescription = "Crear evento"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(DarkBlue, Black)))
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(events) { event ->
                    EventCard(
                        event = event,
                        onEventClick = onEventClick,
                        onDeleteClick = { deleteEvent(db, event.title) }
                    )
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onEventClick: (Event) -> Unit,
    onDeleteClick: () -> Unit
) {
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
                    Text(text = event.getFormattedDate())
                    Text(text = "Ubicación: ${event.location}")
                    Text(text = "Alarma: ${if (event.alarm) "Activada" else "Desactivada"}")
                }

                IconButton(
                    onClick = onDeleteClick
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