package com.example.musicapp.controller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import com.example.musicapp.R
import com.example.musicapp.data.CreateNotification

class Media_Service : Service() {

    var mediaPlayer: MediaPlayer = MediaPlayer()
    var notifi: CreateNotification = CreateNotification()
    var notifiManager: NotificationManager? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.thuc_giac)
        mediaPlayer.isLooping = false
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            createChannel()
//        }
//        notifi.Create_Notification(this, 1, 1)



    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
//        stopSelf()
        super.onDestroy()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notifi.CHANNEL_ID,
                "KOD Dev", NotificationManager.IMPORTANCE_LOW
            )
            notifiManager = getSystemService(NotificationManager::class.java)
            if (notifiManager != null) {
                notifiManager!!.createNotificationChannel(channel)
            }
        }
    }

}