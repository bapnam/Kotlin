package com.notificationmusic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notificationmusic.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), Playable {

    lateinit var binding :ActivityMainBinding

    var notificationManager: NotificationManager? = null

    val tracks: MutableList<Track> = ArrayList()

    var createNotification: CreateNotifi = CreateNotifi()
    var position = 0
    var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        popluateTracks()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
        }

        binding.play.setOnClickListener {
            createNotification.createNotification(this, tracks.get(1), R.drawable.ic_baseline_pause_24,
            1, tracks.size - 1)
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                createNotification.CHANNEL_ID,
                "KOD Dev", NotificationManager.IMPORTANCE_LOW
            )
            notificationManager = getSystemService(NotificationManager::class.java)
            if (notificationManager != null) {
                notificationManager!!.createNotificationChannel(channel)
            }
        }
    }

    private fun popluateTracks() {
        tracks.add(Track("Track 2", "Artist 2", R.drawable.t1))
        tracks.add(Track("Track 2", "Artist 2", R.drawable.t2))
        tracks.add(Track("Track 3", "Artist 3", R.drawable.t3))
        tracks.add(Track("Track 4", "Artist 4", R.drawable.t4))
    }

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.extras!!.getString("actionname")
            when (action) {
                createNotification.ACTION_PREVIUOS -> onTrackPrevious()
                createNotification.ACTION_PLAY -> if (isPlaying) {
                    onTrackPause()
                } else {
                    onTrackPlay()
                }
                createNotification.ACTION_NEXT -> onTrackNext()
            }
        }
    }

    override fun onTrackPrevious() {
        position--;
        createNotification.createNotification(this, tracks.get(position),
            R.drawable.ic_baseline_pause_24, position, tracks.size-1)
//        title.setText(tracks.get(position).getTitle())
    }

    override fun onTrackPlay() {
        createNotification.createNotification(this, tracks.get(position),
            R.drawable.ic_baseline_pause_24, position, tracks.size-1)
        binding.play.setBackgroundResource(R.drawable.ic_baseline_pause_24)
//        title.setText(tracks.get(position).getTitle());
        isPlaying = true;
    }

    override fun onTrackPause() {
        createNotification.createNotification(this, tracks.get(position),
            R.drawable.ic_baseline_play_arrow_24, position, tracks.size-1);
        binding.play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
//        title.setText(tracks.get(position).getTitle());
        isPlaying = false;
    }

    override fun onTrackNext() {
        position++;
        createNotification.createNotification(this, tracks.get(position),
            R.drawable.ic_baseline_pause_24, position, tracks.size-1);
//        title.setText(tracks.get(position).getTitle());
    }

    override fun onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager!!.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);
    }
}