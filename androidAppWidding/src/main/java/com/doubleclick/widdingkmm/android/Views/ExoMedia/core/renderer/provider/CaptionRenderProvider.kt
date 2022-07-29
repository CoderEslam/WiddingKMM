
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider

import android.os.Handler
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.RendererType
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.text.TextOutput
import com.google.android.exoplayer2.text.TextRenderer

class CaptionRenderProvider: RenderProvider {
  private var latestRenderers: List<Renderer> = emptyList()

  override fun type() = RendererType.CLOSED_CAPTION

  override fun rendererClasses(): List<String> {
    return emptyList()
  }

  override fun getLatestRenderers(): List<Renderer> {
    return latestRenderers
  }

  fun buildRenderers(handler: Handler, captionListener: TextOutput): List<Renderer> {
    return listOf(
      TextRenderer(captionListener, handler.looper)
    ).also {
      latestRenderers = it
    }
  }
}