
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.data

import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.TransferListener

/**
 * A Provider for [DataSource.Factory] instances. This is useful
 * for specifying custom data source backing instances such as
 * using OKHTTP instead of the included Android http clients
 */
interface DataSourceFactoryProvider {

  /**
   * Provides a [DataSource.Factory] to use when requesting network
   * data to load media.
   *
   * @param userAgent The "UserAgent" to use when making network requests
   * @param listener A [TransferListener] to inform of data load & transfer events
   * @return An instance of [DataSource.Factory]
   */
  fun provide(userAgent: String, listener: TransferListener?): DataSource.Factory
}