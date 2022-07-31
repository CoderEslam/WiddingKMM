package com.doubleclick.widdings.Views.PhotoView

import android.annotation.TargetApi
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
internal object Compat {
    private const val SIXTY_FPS_INTERVAL = 1000 / 60
    fun postOnAnimation(view: View, runnable: Runnable) {
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            postOnAnimationJellyBean(view, runnable)
        } else {
            view.postDelayed(runnable, SIXTY_FPS_INTERVAL.toLong())
        }
    }

    @TargetApi(16)
    private fun postOnAnimationJellyBean(view: View, runnable: Runnable) {
        view.postOnAnimation(runnable)
    }
}