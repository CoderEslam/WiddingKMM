
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.builder

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource

class DashMediaSourceBuilder : MediaSourceBuilder() {
  override fun build(attributes: MediaSourceAttributes): MediaSource {
    val factoryAttributes = attributes.copy(
        transferListener = null
    )

    val dataSourceFactory = buildDataSourceFactory(factoryAttributes)
    val meteredDataSourceFactory = buildDataSourceFactory(attributes)
    val mediaItem = MediaItem.Builder().setUri(attributes.uri).build()

    return DashMediaSource.Factory(DefaultDashChunkSource.Factory(meteredDataSourceFactory), dataSourceFactory)
        .setDrmSessionManagerProvider(attributes.drmSessionManagerProvider)
        .createMediaSource(mediaItem)
  }
}
