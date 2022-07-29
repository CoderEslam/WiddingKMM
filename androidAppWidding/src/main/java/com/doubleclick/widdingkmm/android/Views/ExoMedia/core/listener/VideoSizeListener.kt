package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.listener

import com.google.android.exoplayer2.video.VideoSize

interface VideoSizeListener {
  fun onVideoSizeChanged(videoSize: VideoSize)
}