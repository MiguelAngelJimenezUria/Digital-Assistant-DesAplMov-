package com.shrimpdevs.digitalassistant.screens.event

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.shrimpdevs.digitalassistant.R
import com.shrimpdevs.digitalassistant.models.Event
import com.shrimpdevs.digitalassistant.ui.theme.Black
import com.shrimpdevs.digitalassistant.ui.theme.DarkBlue
import com.shrimpdevs.digitalassistant.ui.theme.DarkText
import com.shrimpdevs.digitalassistant.ui.theme.White

@Composable
fun CreateEvent(db: FirebaseFirestore, navigateToEvent: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    createEvent(db)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, Black))),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier.weight(1f))
//ookami.6910@gmail.com
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Icon",
            tint = White,
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable {navigateToEvent()}
                .align(Alignment.Start)
                .size(45.dp)
                .shadow(10.dp, shape = RoundedCornerShape(15.dp))
        )
        Button(
            onClick = {
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
