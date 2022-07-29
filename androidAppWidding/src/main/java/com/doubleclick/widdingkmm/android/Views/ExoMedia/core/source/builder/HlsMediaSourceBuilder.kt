
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.builder

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource

class HlsMediaSourceBuilder : MediaSourceBuilder() {
  override fun build(attributes: MediaSourceAttributes): MediaSource {
    val dataSourceFactory = buildDataSourceFactory(attributes)
    val mediaItem = MediaItem.Builder().setUri(attributes.uri).build()

    return HlsMediaSource.Factory(dataSourceFactory)
        .setDrmSessionManagerProvider(attributes.drmSessionManagerProvider)
        .createMediaSource(mediaItem)
  }
}
