
package com.doubleclick.widdingkmm.android.Views.ExoMedia.listener

/**
 * Interface definition of a callback to be invoked when there
 * has been an error during an asynchronous operation.
 */
interface OnErrorListener {
  /**
   * Called to indicate an error has occurred. In the case that the error was
   * caused by the Native [android.media.MediaPlayer] `e` should
   * be of the type [com.doubleclick.widdingkmm.android.Views.ExoMedia.core.exception.NativeMediaPlaybackException]
   * while all other exceptions will be provided by the [com.google.android.exoplayer2.ExoPlayer]
   * implementation directly.
   *
   * @param e The [Exception] that caused or describes the error
   * @return True if the method handled the error, false if it didn't.
   */
  fun onError(e: Exception?): Boolean
}