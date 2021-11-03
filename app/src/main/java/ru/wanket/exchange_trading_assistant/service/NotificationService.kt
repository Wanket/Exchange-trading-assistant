package ru.wanket.exchange_trading_assistant.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.exchange_trading_assistant.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationService @Inject constructor() {
    companion object {
        const val GLOBAL_CHANNEL_ID = "global_channel_id"
    }

    fun load(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(
            NotificationChannel(GLOBAL_CHANNEL_ID, "Global", NotificationManager.IMPORTANCE_DEFAULT)
        )
    }

    fun show(context: Context, title: String, message: String, clickIntent: Intent? = null) {
        NotificationCompat.Builder(context, GLOBAL_CHANNEL_ID).apply {
            setContentTitle(title)
            setContentText(message)

            setSmallIcon(R.drawable.ic_launcher_foreground)
            setStyle(NotificationCompat.BigTextStyle().bigText(message))
            setAutoCancel(true)

            if (clickIntent != null) {
                setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                )
            }

            NotificationManagerCompat.from(context)
                .notify(System.currentTimeMillis().toInt(), build())
        }
    }

    fun isNotificationsEnabled(context: Context) = NotificationManagerCompat.from(context).run {
        getNotificationChannel(GLOBAL_CHANNEL_ID)!!.importance != NotificationManager.IMPORTANCE_NONE && areNotificationsEnabled()
    }
}
