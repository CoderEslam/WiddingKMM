
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.builder

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource

class DefaultMediaSourceBuilder : MediaSourceBuilder() {
  override fun build(attributes: MediaSourceAttributes): MediaSource {
    val dataSourceFactory = buildDataSourceFactory(attributes)
    val mediaItem = MediaItem.Builder().setUri(attributes.uri).build()

    return ProgressiveMediaSource.Factory(dataSourceFactory, DefaultExtractorsFactory())
        .setDrmSessionManagerProvider(attributes.drmSessionManagerProvider)
        .createMediaSource(mediaItem)
  }
}
