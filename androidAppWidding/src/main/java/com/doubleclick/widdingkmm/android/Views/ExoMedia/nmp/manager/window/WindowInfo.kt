
package com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.manager.window

import com.google.android.exoplayer2.Timeline

data class WindowInfo(
    val previousWindowIndex: Int,
    val currentWindowIndex: Int,
    val nextWindowIndex: Int,
    val currentWindow: Timeline.Window
)