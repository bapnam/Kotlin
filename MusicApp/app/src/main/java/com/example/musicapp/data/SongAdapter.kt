package com.example.musicapp.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.musicapp.R
import java.util.ArrayList

class SongAdapter(
    c: Context?, // Song list và layout
    private val songs: ArrayList<Song>
) : BaseAdapter() {

    private val songInf: LayoutInflater = LayoutInflater.from(c)

    override fun getCount(): Int {
        return songs.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val songLayout = songInf.inflate(R.layout.song, parent, false) as LinearLayout
        val titleView = songLayout.findViewById<View>(R.id.txt_songtitle) as TextView
        val artistView = songLayout.findViewById<View>(R.id.txt_songartist) as TextView

        // Lấy bài hát hiện
        val currentSong = songs[position]

        // Lấy tên tiêu đề và tác
        titleView.text = currentSong.title
        artistView.text = currentSong.artist

        // Cài đặt tag cho mỗi bài là vị trí của mỗi
        songLayout.tag = position
        return songLayout
    }
}