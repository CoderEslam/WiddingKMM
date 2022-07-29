
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider

import android.content.Context
import android.os.Handler
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.RendererType
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer
import com.google.android.exoplayer2.video.VideoRendererEventListener

class VideoRenderProvider(
  private val droppedFrameNotificationAmount: Int = 50,
  private val videoJoiningTimeMs: Long = 5_000L
) : RenderProvider {
  private var latestRenderers: List<Renderer> = emptyList()

  override fun type() = RendererType.VIDEO

  override fun rendererClasses(): List<String> {
    return listOf(
      "com.google.android.exoplayer2.ext.vp9.LibvpxVideoRenderer"
    )
  }

  override fun getLatestRenderers(): List<Renderer> {
    return latestRenderers
  }

   fun buildRenderers(context: Context, handler: Handler, listener: VideoRendererEventListener): List<Renderer> {
    val renderers = mutableListOf<Renderer>()
    renderers.add(MediaCodecVideoRenderer(context, MediaCodecSelector.DEFAULT, videoJoiningTimeMs, handler, listener, droppedFrameNotificationAmount))

    // Adds any registered classes
    rendererClasses().forEach { className ->
      try {
        val clazz = Class.forName(className)
        val constructor = clazz.getConstructor(Boolean::class.javaPrimitiveType, Long::class.javaPrimitiveType, Handler::class.java, VideoRendererEventListener::class.java, Int::class.javaPrimitiveType)
        val renderer = constructor.newInstance(true, videoJoiningTimeMs, handler, listener, droppedFrameNotificationAmount) as Renderer
        renderers.add(renderer)
      } catch (e: Exception) {
        // Purposefully left blank
      }
    }

    return renderers.also {
      latestRenderers = it
    }
  }
}