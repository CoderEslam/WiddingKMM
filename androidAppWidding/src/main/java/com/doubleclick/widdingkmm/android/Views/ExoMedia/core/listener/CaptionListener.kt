
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.listener


import com.google.android.exoplayer2.text.Cue

/**
 * A repeatListener for receiving notifications of timed text.
 */
interface CaptionListener {
  fun onCues(cues: List<Cue>)
}
