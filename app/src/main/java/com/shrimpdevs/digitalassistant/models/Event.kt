package com.shrimpdevs.digitalassistant.models
import com.google.firebase.Timestamp

data class Event(
    val title: String,
    val description: String,
    val eventDate: Timestamp,
    val location: String,
    val alarm: Boolean,
)