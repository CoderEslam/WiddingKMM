package com.doubleclick.widdings.Views.PhotoView

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
open interface OnScaleChangedListener {
    /**
     * Callback for when the scale changes
     *
     * @param scaleFactor the scale factor (less than 1 for zoom out, greater than 1 for zoom in)
     * @param focusX      focal point X position
     * @param focusY      focal point Y position
     */
    fun onScaleChange(scaleFactor: Float, focusX: Float, focusY: Float)
}
