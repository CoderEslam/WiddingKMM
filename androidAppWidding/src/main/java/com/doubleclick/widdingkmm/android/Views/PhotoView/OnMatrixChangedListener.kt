package com.doubleclick.widdings.Views.PhotoView

import android.graphics.RectF

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
open interface OnMatrixChangedListener {
    /**
     * Callback for when the Matrix displaying the Drawable has changed. This could be because
     * the View's bounds have changed, or the user has zoomed.
     *
     * @param rect - Rectangle displaying the Drawable's new bounds.
     */
    fun onMatrixChanged(rect: RectF?)
}
