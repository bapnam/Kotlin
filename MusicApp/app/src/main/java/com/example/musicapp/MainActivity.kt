package com.example.musicapp

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.example.musicapp.data.Song
import com.example.musicapp.data.SongAdapter
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.view.PlayMusicActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var listMusic: ArrayList<Song>
    lateinit var songAdapter: SongAdapter

    lateinit var media: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listMusic = ArrayList<Song>()
        this.getSongList()
        songAdapter = SongAdapter(this, listMusic)
        binding.lsvDsnhac.adapter = songAdapter

        runtimePermission()

        binding.lsvDsnhac.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, PlayMusicActivity::class.java)
            intent.putExtra("id", listMusic[position].ID)
            intent.putExtra("name", listMusic[position].title)
            intent.putExtra("artist", listMusic[position].artist)

            Toast.makeText(this, "${(listMusic[position].path)}", Toast.LENGTH_SHORT).show()
            startActivity(intent)

//            media = MediaPlayer()
//
//            val currSong: Long = listMusic[position].ID.toLong()
//            val trackUri: Uri = ContentUris.withAppendedId(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                currSong
//            )
//
//            try {
//                //media = MediaPlayer.create(applicationContext, (listMusic[position].path))
//                //media = MediaPlayer.create(applicationContext, listMusic[position].ID)
//                media.setDataSource(applicationContext, trackUri)
//            } catch (ex: Exception){
//                Log.e("++++Loi", ex.toString())
//                media = MediaPlayer.create(applicationContext, R.raw.thuc_giac)
//            }
//            media.prepareAsync()
//
//            media.start()
        }

    }

    fun runtimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                }
            }
        }
    }

    fun getSongList() {
        /// Query external audio resources
        var musicResolver = contentResolver
        var musicURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var musicCursor = musicResolver.query(musicURI,
        null, null, null,
        MediaStore.Audio.Media.TITLE + " ASC")

        /// Iterate over results if valid
        if (musicCursor != null && musicCursor.moveToFirst()){
            /// Get columns
            val id = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artist = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val path = musicCursor.getColumnIndex(MediaStore.Audio.Media.RELATIVE_PATH)
            do {
                val thisID = musicCursor.getInt(id)
                val thisTitle = musicCursor.getString(title)
                val thisArtist = musicCursor.getString(artist)
                val thisPath = musicCursor.getString(path).toUri()
                listMusic.add(Song(thisID, thisTitle, thisArtist, thisPath))
            } while (musicCursor.moveToNext())
        }
    }



}