package com.notificationmusic

interface Playable {
    fun onTrackPrevious()
    fun onTrackPlay()
    fun onTrackPause()
    fun onTrackNext()
}