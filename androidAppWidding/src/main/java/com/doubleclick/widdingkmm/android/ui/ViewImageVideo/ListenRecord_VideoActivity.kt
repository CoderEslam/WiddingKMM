package com.doubleclick.widdingkmm.android.ui.ViewImageVideo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.ExoMedia.listener.OnPreparedListener
import com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.VideoView

class ListenRecord_VideoActivity : AppCompatActivity(), OnPreparedListener {

    private lateinit var video: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listen_record_video)
        video = findViewById(R.id.video);
        video.setVideoURI(Uri.parse(intent.getStringExtra("url").toString()))
    }

    override fun onPrepared() {
        video.start()
    }
}