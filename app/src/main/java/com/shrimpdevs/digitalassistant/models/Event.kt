package com.shrimpdevs.digitalassistant.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class Event(
    var title: String = "",
    var description: String = "",
    var eventDate: Timestamp = Timestamp.now(),
    var location: String = "",
    var alarm: Boolean = false
) : Serializable