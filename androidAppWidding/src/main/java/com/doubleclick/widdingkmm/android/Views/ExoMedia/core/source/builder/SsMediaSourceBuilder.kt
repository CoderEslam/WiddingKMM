
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.builder

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource

class SsMediaSourceBuilder : MediaSourceBuilder() {
  override fun build(attributes: MediaSourceAttributes): MediaSource {
    val factoryAttributes = attributes.copy(
        transferListener = null
    )

    val dataSourceFactory = buildDataSourceFactory(factoryAttributes)
    val meteredDataSourceFactory = buildDataSourceFactory(attributes)
    val mediaItem = MediaItem.Builder().setUri(attributes.uri).build()

    return SsMediaSource.Factory(DefaultSsChunkSource.Factory(meteredDataSourceFactory), dataSourceFactory)
        .setDrmSessionManagerProvider(attributes.drmSessionManagerProvider)
        .createMediaSource(mediaItem)
  }
}
