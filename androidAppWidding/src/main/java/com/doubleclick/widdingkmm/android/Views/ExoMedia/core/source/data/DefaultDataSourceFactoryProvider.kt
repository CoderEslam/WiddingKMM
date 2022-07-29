
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.data

import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.TransferListener

class DefaultDataSourceFactoryProvider: DataSourceFactoryProvider {
  override fun provide(userAgent: String, listener: TransferListener?): DataSource.Factory {
    return DefaultHttpDataSource.Factory().apply {
      setUserAgent(userAgent)
      setTransferListener(listener)
    }
  }
}