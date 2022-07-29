
package com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp

import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.listener.CaptionListener
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.listener.MetadataListener
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.listener.VideoSizeListener
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.text.Cue
import com.google.android.exoplayer2.video.VideoSize

internal class DelegatedRenderListener: Player.Listener {
  private var captionListener: CaptionListener? = null
  private var metadataListener: MetadataListener? = null
  private var videoSizeListener: VideoSizeListener? = null

  fun setCaptionListener(listener: CaptionListener?) {
    captionListener = listener
  }

  fun setMetadataListener(listener: MetadataListener?) {
    metadataListener = listener
  }

  fun setVideoSizeListener(listener: VideoSizeListener?) {
    videoSizeListener = listener
  }

  override fun onVideoSizeChanged(videoSize: VideoSize) {
    videoSizeListener?.onVideoSizeChanged(videoSize)
  }

  override fun onMetadata(metadata: Metadata) {
    metadataListener?.onMetadata(metadata)
  }

  override fun onCues(cues: List<Cue>) {
    captionListener?.onCues(cues)
  }
}