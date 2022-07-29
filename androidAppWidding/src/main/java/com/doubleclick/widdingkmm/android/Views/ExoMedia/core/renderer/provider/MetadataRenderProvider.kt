
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider

import android.os.Handler
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.RendererType
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.metadata.MetadataDecoderFactory
import com.google.android.exoplayer2.metadata.MetadataOutput
import com.google.android.exoplayer2.metadata.MetadataRenderer

class MetadataRenderProvider: RenderProvider {
  private var latestRenderers: List<Renderer> = emptyList()

  override fun type() = RendererType.METADATA

  override fun rendererClasses(): List<String> {
    return emptyList()
  }

  override fun getLatestRenderers(): List<Renderer> {
    return latestRenderers
  }

  fun buildRenderers(handler: Handler, metadataListener: MetadataOutput): List<Renderer> {
    return listOf(
      MetadataRenderer(metadataListener, handler.looper, MetadataDecoderFactory.DEFAULT)
    ).also {
      latestRenderers = it
    }
  }
}