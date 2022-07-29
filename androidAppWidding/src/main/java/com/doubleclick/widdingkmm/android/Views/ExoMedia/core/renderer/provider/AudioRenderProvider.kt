
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider

import android.content.Context
import android.os.Handler
import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.RendererType
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.audio.AudioCapabilities
import com.google.android.exoplayer2.audio.AudioRendererEventListener
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector

open class AudioRenderProvider : RenderProvider {
  private var latestRenderers: List<Renderer> = emptyList()

  override fun type() = RendererType.AUDIO

  override fun rendererClasses(): List<String> {
    return listOf(
      "com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer",
      "com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer",
      "com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer",
    )
  }

  override fun getLatestRenderers(): List<Renderer> {
    return latestRenderers
  }

  fun buildRenderers(context: Context, handler: Handler, listener: AudioRendererEventListener): List<Renderer> {
    val renderers = mutableListOf<Renderer>()

    val audioCapabilities = AudioCapabilities.getCapabilities(context)
    renderers.add(MediaCodecAudioRenderer(context, MediaCodecSelector.DEFAULT, handler, listener, audioCapabilities))

    // Adds any registered classes
    rendererClasses().forEach { className ->
      try {
        val clazz = Class.forName(className)
        val constructor = clazz.getConstructor(Handler::class.java, AudioRendererEventListener::class.java)
        val renderer = constructor.newInstance(handler, listener) as Renderer
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