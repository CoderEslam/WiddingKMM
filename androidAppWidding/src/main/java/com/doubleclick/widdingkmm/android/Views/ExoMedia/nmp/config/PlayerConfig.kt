
package com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.config

import android.content.Context
import android.os.Handler
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.MediaSourceProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.source.data.DataSourceFactoryProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.manager.UserAgentProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.manager.WakeManager
import com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.manager.track.TrackManager
import com.doubleclick.widdingkmm.android.Views.ExoMedia.util.FallbackManager
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.analytics.AnalyticsCollector
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.upstream.BandwidthMeter

/**
 * Supplies the classes necessary for the [com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.ExoMediaPlayerImpl]
 * to play media. It is recommended to use the [PlayerConfigBuilder] to construct a new
 * instance as that will provide default implementations.
 */
data class PlayerConfig(
  val context: Context,
  val fallbackManager: FallbackManager,
  val analyticsCollector: AnalyticsCollector,
  val bandwidthMeter: BandwidthMeter,
  val handler: Handler,
  val rendererFactory: RenderersFactory,
  val trackManager: TrackManager,
  val wakeManager: WakeManager,
  val loadControl: LoadControl,
  val userAgentProvider: UserAgentProvider,
  val mediaSourceProvider: MediaSourceProvider,
  val mediaSourceFactory: MediaSourceFactory,
  val dataSourceFactoryProvider: DataSourceFactoryProvider
)