
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.builder

import android.content.Context
import android.net.Uri
import android.os.Handler
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.data.DataSourceFactoryProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.data.DefaultDataSourceFactoryProvider
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener

abstract class MediaSourceBuilder {
  data class MediaSourceAttributes(
      val context: Context,
      val uri: Uri,
      val handler: Handler,
      val userAgent: String,
      val transferListener: TransferListener? = null,
      val drmSessionManagerProvider: DrmSessionManagerProvider? = null,
      val dataSourceFactoryProvider: DataSourceFactoryProvider = DefaultDataSourceFactoryProvider()
  )

  abstract fun build(attributes: MediaSourceAttributes): MediaSource

  fun buildDataSourceFactory(attributes: MediaSourceAttributes): DataSource.Factory {
    val dataSourceFactory = attributes.dataSourceFactoryProvider.provide(attributes.userAgent, attributes.transferListener)
    return DefaultDataSourceFactory(attributes.context, attributes.transferListener, dataSourceFactory)
  }
}
