
package com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.provider

import com.doubleclick.widdingkmm.android.Views.ExoMedia.core.renderer.RendererType
import com.google.android.exoplayer2.Renderer

/**
 * Provides the capabilities for building renderers
 */
interface RenderProvider {

  /**
   * The [RendererType] that this provider builds
   */
  fun type(): RendererType

  /**
   * the list of pre-defined classes to that the provider supports
   */
  fun rendererClasses(): List<String>

  /**
   * Retrieves the latest built renderers
   */
  fun getLatestRenderers(): List<Renderer>
}