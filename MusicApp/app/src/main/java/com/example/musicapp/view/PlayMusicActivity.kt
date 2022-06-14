package com.example.musicapp.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.controller.Media_Service
import com.example.musicapp.data.CreateNotification
import com.example.musicapp.databinding.ActivityPlayMusicBinding

class PlayMusicActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayMusicBinding

    lateinit var intentService: Intent
    var notifi: CreateNotification = CreateNotification()
    var notifiManager: NotificationManager? = null


    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.extras

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
        }

        binding.ibtPlaypause.setOnClickListener {
            binding.txtNamesong.text = intent.getStringExtra("name")
            intentService = Intent(this, Media_Service::class.java)
            startService(intentService)
            notifi.Create_Notification(this, 1, 1)

        }

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

    override fun onDestroy() {
//        stopService(intentService)
        super.onDestroy()
    }

}