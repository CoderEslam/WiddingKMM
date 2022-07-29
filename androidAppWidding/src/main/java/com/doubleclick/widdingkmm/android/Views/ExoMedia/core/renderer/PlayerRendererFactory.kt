
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer

import android.content.Context
import android.os.Handler
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider.AudioRenderProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider.CaptionRenderProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider.MetadataRenderProvider
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider.VideoRenderProvider
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.audio.AudioRendererEventListener
import com.google.android.exoplayer2.metadata.MetadataOutput
import com.google.android.exoplayer2.text.TextOutput
import com.google.android.exoplayer2.video.VideoRendererEventListener

class PlayerRendererFactory(
  private val context: Context
): RenderersFactory {
  override fun createRenderers(
    eventHandler: Handler,
    videoRendererEventListener: VideoRendererEventListener,
    audioRendererEventListener: AudioRendererEventListener,
    textRendererOutput: TextOutput,
    metadataRendererOutput: MetadataOutput
  ): Array<Renderer> {
    return mutableListOf<Renderer>().apply {
      addAll(AudioRenderProvider().buildRenderers(context, eventHandler, audioRendererEventListener))
      addAll(VideoRenderProvider().buildRenderers(context, eventHandler, videoRendererEventListener))
      addAll(CaptionRenderProvider().buildRenderers(eventHandler, textRendererOutput))
      addAll(MetadataRenderProvider().buildRenderers(eventHandler, metadataRendererOutput))
    }.toTypedArray()
  }
}