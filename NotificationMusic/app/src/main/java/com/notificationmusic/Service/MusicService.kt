package com.notificationmusic.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MusicService : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        context!!.sendBroadcast( Intent("TRACKS_TRACKS")
            .putExtra("actionname", intent!!.getAction()));
    }


}