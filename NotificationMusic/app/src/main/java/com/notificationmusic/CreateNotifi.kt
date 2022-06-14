package com.notificationmusic


import android.R.attr
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.notificationmusic.Service.MusicService


class CreateNotifi {

    val CHANNEL_ID = "channel1"

    val ACTION_PREVIUOS = "actionprevious"
    val ACTION_PLAY = "actionplay"
    val ACTION_NEXT = "actionnext"

    var notification: Notification? = null

    fun createNotification(context: Context, track: Track, playbutton: Int, pos: Int, size: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionCompat = MediaSessionCompat(context, "tag")
            val icon = BitmapFactory.decodeResource(context.getResources(), track.getImage())

            val pendingIntentPrevious: PendingIntent?
            val drw_previous: Int
            if (pos === 0) {
                pendingIntentPrevious = null
                drw_previous = 0
            } else {
                val intentPrevious = Intent(context, MusicService::class.java)
                    .setAction(ACTION_PREVIUOS)
                pendingIntentPrevious = PendingIntent.getBroadcast(
                    context, 0,
                    intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT
                )
                drw_previous = R.drawable.ic_baseline_skip_previous_24
            }

            val intentPlay = Intent(context, MusicService::class.java)
                .setAction(ACTION_PLAY)
            val pendingIntentPlay = PendingIntent.getBroadcast(
                context, 0,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT
            )

            val pendingIntentNext: PendingIntent?
            val drw_next: Int
            if (pos === size) {
                pendingIntentNext = null
                drw_next = 0
            } else {
                val intentNext = Intent(context, MusicService::class.java)
                    .setAction(ACTION_NEXT)
                pendingIntentNext = PendingIntent.getBroadcast(
                    context, 0,
                    intentNext, PendingIntent.FLAG_UPDATE_CURRENT
                )
                drw_next = R.drawable.ic_baseline_skip_next_24
            }

            //create notification
            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                .setContentTitle(track.getTitle())
                .setContentText(track.getArtist())
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true) //show notification for only first time
                .setShowWhen(false)
                .addAction(drw_previous, "Previous", pendingIntentPrevious)
                .addAction(playbutton, "Play", pendingIntentPlay)
                .addAction(drw_next, "Next", pendingIntentNext)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.sessionToken)
                )
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            notificationManagerCompat.notify(1, notification!!)
        }
    }

}