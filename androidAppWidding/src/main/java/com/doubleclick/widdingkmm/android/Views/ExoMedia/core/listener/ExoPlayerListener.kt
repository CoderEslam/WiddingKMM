
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.listener

import com.doubleclick.widdingkmm.android.Views.ExoMedia.listener.OnSeekCompletionListener
import com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.ExoMediaPlayer

/**
 * A listener for core [ExoMediaPlayer] events
 */
interface ExoPlayerListener : OnSeekCompletionListener {
  fun onStateChanged(playWhenReady: Boolean, playbackState: Int)

  fun onError(player: ExoMediaPlayer, e: Exception?)

  fun onVideoSizeChanged(width: Int, height: Int, unAppliedRotationDegrees: Int, pixelWidthHeightRatio: Float)
}
