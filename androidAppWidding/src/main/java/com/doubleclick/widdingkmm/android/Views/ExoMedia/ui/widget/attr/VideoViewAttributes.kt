
package com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.attr

import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.video.scale.ScaleType
import com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.config.DefaultPlayerConfigProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.config.PlayerConfigProvider

data class VideoViewAttributes(
    /**
     * Specifies if the [DefaultVideoControls] should be added to the view.  These
     * can be added through source code with [.setControls]
     */
    val useDefaultControls: Boolean = false,

    /**
     * Specifies if the [VideoViewApi] implementations should use the [android.view.TextureView]
     * implementations.  If this is false then the implementations will be based on
     * the [android.view.SurfaceView]
     */
    val useTextureViewBacking: Boolean = false,

    /**
     * Specifies the scale that the [VideoView] should use. If this is `null`
     * then the default value from the [com.doubleclick.widdingkmm.android.Views.ExoMedia.core.video.scale.MatrixManager]
     * will be used.
     */
    val scaleType: ScaleType? = null,

    /**
     * Specifies if the [VideoView] should be measured based on the aspect ratio. Because
     * the default value is different between the [com.doubleclick.widdingkmm.android.Views.ExoMedia.core.video.ResizingSurfaceView]
     * and [com.doubleclick.widdingkmm.android.Views.ExoMedia.core.video.ResizingTextureView] this will be `null`
     * when not specified.
     */
    val measureBasedOnAspectRatio: Boolean? = null,

    /**
     * Specifies the provider to use when fetching the configuration for the
     * media playback.
     */
    val playerConfigProvider: PlayerConfigProvider = DefaultPlayerConfigProvider()
)