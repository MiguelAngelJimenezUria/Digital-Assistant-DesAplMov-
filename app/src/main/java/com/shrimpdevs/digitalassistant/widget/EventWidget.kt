package com.shrimpdevs.digitalassistant.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shrimpdevs.digitalassistant.MainActivity
import com.shrimpdevs.digitalassistant.R
import java.text.SimpleDateFormat
import java.util.Locale

class EventWidget : AppWidgetProvider() {

    companion object {
        // Método para actualizar todos los widgets existentes
        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, EventWidget::class.java)
            )
            val intent = Intent(context, EventWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
            }
            context.sendBroadcast(intent)
        }
    }

    // Maneja la recepción de intents para actualizar el widget
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, EventWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    // Método llamado cuando se necesita actualizar los widgets
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // Método principal para actualizar el contenido del widget
    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.event_widget)
        val auth = FirebaseAuth.getInstance()

        // Intent para ir a la pantalla inicial (login)
        val loginIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("destination", "initial")
        }
        val loginPendingIntent = PendingIntent.getActivity(
            context, 1, loginIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent para crear nuevo evento
        val createEventIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("destination", "createEvent")
        }
        val createEventPendingIntent = PendingIntent.getActivity(
            context, 0, createEventIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (auth.currentUser != null) {
            // Configuración para usuario autenticado
            views.setViewVisibility(R.id.logged_out_layout, View.GONE)
            views.setViewVisibility(R.id.logged_in_layout, View.VISIBLE)
            views.setOnClickPendingIntent(R.id.add_event_button, createEventPendingIntent)

            // Obtener eventos de Firebase
            FirebaseFirestore.getInstance()
                .collection("events")
                .orderBy("eventDate")
                .limit(3)
                .get()
                .addOnSuccessListener { documents ->
                    var eventText = ""
                    val dateFormat = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())

                    for (document in documents) {
                        val title = document.getString("title") ?: ""
                        val date = document.getTimestamp("eventDate")?.toDate()
                        val formattedDate = date?.let { dateFormat.format(it) } ?: ""
                        eventText += "📅 $formattedDate\n$title\n\n"
                    }

                    views.setTextViewText(R.id.widget_text, eventText.trim())
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
                .addOnFailureListener {
                    views.setTextViewText(R.id.widget_text, "Error al cargar eventos")
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
        } else {
            // Configuración para usuario no autenticado
            views.setViewVisibility(R.id.logged_out_layout, View.VISIBLE)
            views.setViewVisibility(R.id.logged_in_layout, View.GONE)
            views.setOnClickPendingIntent(R.id.login_button, loginPendingIntent)
        }

        // Actualización inicial del widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}