package com.shrimpdevs.digitalassistant

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shrimpdevs.digitalassistant.ui.theme.DigitalAssistantTheme

val db = Firebase.firestore

val user = hashMapOf(
    "first" to "Ada",
    "last" to "Lovelace",
    "born" to 1815
)

// Add a new document with a generated ID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalAssistantTheme {
                ScreenMain()
            }
        }
    }
}

@Composable
fun ScreenMain() {

}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    DigitalAssistantTheme {
        ScreenMain()
    }
}