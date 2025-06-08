package com.shrimpdevs.digitalassistant.screens

import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore


import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.shrimpdevs.digitalassistant.ui.theme.Black
import com.shrimpdevs.digitalassistant.ui.theme.DarkBlue
import com.shrimpdevs.digitalassistant.ui.theme.DarkText


@Composable
fun HomeScreen(db: FirebaseFirestore) {
    val db = Firebase.firestore


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, Black)))
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick =  {
                createEvent(db)
                },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 32.dp)
                .shadow(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkText,
                disabledContainerColor = DarkText.copy(alpha = 0.6f)
            )
        ) {
            Text(text = "Crear evento")
        }
    }
}


data class Event(
    val title: String,
    val description: String,
    val eventDate: Timestamp,
    val location: String,
    val alarm: Boolean,
)

fun createEvent(db: FirebaseFirestore)  {
    val random = (1..1000).random()
    val event = Event(title = "Random $random", description = "Random $random", eventDate = Timestamp.now(), location = "Random $random", alarm = false)
    db.collection("events")
        .add(event)
        .addOnSuccessListener {
            Log.i("Login", "SUCCESS")
        }
        .addOnFailureListener {
            Log.i("Login", "FAILURE")
        }
        .addOnCompleteListener {
            Log.i("Login", "COMPLETE ")        }
}