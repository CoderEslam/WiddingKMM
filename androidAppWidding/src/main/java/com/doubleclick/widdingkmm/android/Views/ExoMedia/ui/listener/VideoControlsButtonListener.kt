
package com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.listener

interface VideoControlsButtonListener {
  /**
   * Occurs when the PlayPause button on the [com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.DefaultVideoControls]
   * is clicked
   *
   * @return True if the event has been handled
   */
  fun onPlayPauseClicked(): Boolean

  /**
   * Occurs when the Previous button on the [com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.DefaultVideoControls]
   * is clicked
   *
   * @return True if the event has been handled
   */
  fun onPreviousClicked(): Boolean

  /**
   * Occurs when the Next button on the [com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.DefaultVideoControls]
   * is clicked
   *
   * @return True if the event has been handled
   */
  fun onNextClicked(): Boolean

  /**
   * Occurs when the Rewind button on the [com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.DefaultVideoControls]
   * is clicked.
   *
   * @return True if the event has been handled
   */
  fun onRewindClicked(): Boolean

  /**
   * Occurs when the Fast Forward button on the [com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.DefaultVideoControls]
   * is clicked.
   *
   * @return True if the event has been handled
   */
  fun onFastForwardClicked(): Boolean
}
