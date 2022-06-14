package com.notificationmusic

class Track {

    private var title: String? = null
    private var artist: String? = null
    private var image: Int = 0

    constructor(title: String?, artist: String?, image: Int) {
        this.title = title
        this.artist = artist
        this.image = image
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getArtist(): String? {
        return artist
    }

    fun setArtist(artist: String?) {
        this.artist = artist
    }

    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
    }

}