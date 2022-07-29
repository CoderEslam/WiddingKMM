package com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.config

import android.content.Context

/**
 * A simple provider for the [PlayerConfig]
 */
interface PlayerConfigProvider {
  /**
   * Provide the [PlayerConfig] for the requested context
   */
  fun getConfig(context: Context): PlayerConfig
}