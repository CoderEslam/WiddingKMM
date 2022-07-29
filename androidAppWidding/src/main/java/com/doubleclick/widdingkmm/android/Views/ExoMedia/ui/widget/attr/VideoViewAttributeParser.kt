
package com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.attr

import android.content.Context
import android.util.AttributeSet
import com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.attr.VideoViewAttributes
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.video.scale.ScaleType
import com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.config.DefaultPlayerConfigProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.nmp.config.PlayerConfigProvider

class VideoViewAttributeParser {

  fun parse(context: Context, attrs: AttributeSet?): VideoViewAttributes {
    if (attrs == null) {
      return VideoViewAttributes()
    }

    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VideoView)

    val useDefaultControls = typedArray.getBoolean(R.styleable.VideoView_useDefaultControls, false)
    val useTextureViewBacking = typedArray.getBoolean(R.styleable.VideoView_useTextureViewBacking, false)

    val scaleType = if (typedArray.hasValue(R.styleable.VideoView_videoScale)) {
      ScaleType.fromOrdinal(typedArray.getInt(R.styleable.VideoView_videoScale, -1))
    } else {
      null
    }

    val measureBasedOnAspectRatio = if (typedArray.hasValue(R.styleable.VideoView_measureBasedOnAspectRatio)) {
      typedArray.getBoolean(R.styleable.VideoView_measureBasedOnAspectRatio, false)
    } else {
      null
    }

    val configProvider = typedArray.getString(R.styleable.VideoView_playerConfigProvider)?.let {
      Class.forName(it).getConstructor().newInstance() as PlayerConfigProvider
    } ?: DefaultPlayerConfigProvider()

    typedArray.recycle()

    return VideoViewAttributes(
        useDefaultControls = useDefaultControls,
        useTextureViewBacking = useTextureViewBacking,
        scaleType = scaleType,
        measureBasedOnAspectRatio = measureBasedOnAspectRatio,
        playerConfigProvider = configProvider
    )
  }
}