

package com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.manager

import androidx.annotation.Size
import com.google.android.exoplayer2.Player

/**
 * Handles tracking the state of an ExoPlayer to help determine playback
 * readiness, seeking, etc.
 */
internal class StateStore {
  companion object {
    const val FLAG_PLAY_WHEN_READY = -0x10000000
    const val STATE_SEEKING = 100
  }

  //We keep the last few states because that is all we need currently
  private val prevStates = intArrayOf(Player.STATE_IDLE, Player.STATE_IDLE, Player.STATE_IDLE, Player.STATE_IDLE)

  val mostRecentState: Int
    get() = prevStates[3]

  val isLastReportedPlayWhenReady: Boolean
    get() = prevStates[3] and FLAG_PLAY_WHEN_READY != 0

  fun reset() {
    for (i in prevStates.indices) {
      prevStates[i] = Player.STATE_IDLE
    }
  }

  fun setMostRecentState(playWhenReady: Boolean, state: Int) {
    val newState = getState(playWhenReady, state)
    if (prevStates[3] == newState) {
      return
    }

    prevStates[0] = prevStates[1]
    prevStates[1] = prevStates[2]
    prevStates[2] = prevStates[3]
    prevStates[3] = state
  }

  fun getState(playWhenReady: Boolean, state: Int): Int {
    return state or if (playWhenReady) FLAG_PLAY_WHEN_READY else 0
  }

  fun seekCompleted(): Boolean {
    // Because the playWhenReady isn't a state in itself, rather a flag to a state we will ignore informing of
    // see events when that is the only change.
    if (matchesHistory(intArrayOf(STATE_SEEKING, Player.STATE_BUFFERING, Player.STATE_READY), true)) {
      return true
    }

    // Some devices we get states ordered as [buffering, seeking, ready]
    if (matchesHistory(intArrayOf(Player.STATE_BUFFERING, STATE_SEEKING, Player.STATE_READY), true)) {
      return true
    }

    // Some devices we get states ordered as [seeking, ready, buffering, ready]
    return matchesHistory(intArrayOf(STATE_SEEKING, Player.STATE_READY, Player.STATE_BUFFERING, Player.STATE_READY), true)
  }

  fun matchesHistory(@Size(min = 1, max = 4) states: IntArray, ignorePlayWhenReady: Boolean): Boolean {
    var flag = true
    val andFlag = if (ignorePlayWhenReady) FLAG_PLAY_WHEN_READY.inv() else 0x0.inv()
    val startIndex = prevStates.size - states.size

    for (i in startIndex until prevStates.size) {
      flag = flag and (prevStates[i] and andFlag == states[i - startIndex] and andFlag)
    }

    return flag
  }
}