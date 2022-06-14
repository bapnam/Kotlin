package com.example.musicapp.data

import android.app.Notification
import android.content.Context
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.musicapp.R

class CreateNotification {

    val CHANNEL_ID = "channel1"

    val ACTION_PREVIUOS = "actionprevious"
    val ACTION_PLAY = "actionplay"
    val ACTION_NEXT = "actionnext"

    var notification: Notification? = null

    fun Create_Notification(context: Context, playButton: Int, size: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionCompat = MediaSessionCompat(context, "tag")

            ///Create Notification
            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                .setContentTitle("Helo Notification")
                .setContentText("My Notification")
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.sessionToken)
                )
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()

            notificationManagerCompat.notify(2, notification!!)
        }
    }
}