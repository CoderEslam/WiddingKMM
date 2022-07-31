package com.doubleclick.widdings.Views.PhotoView

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
internal open interface OnGestureListener {
    fun onDrag(dx: Float, dy: Float)
    fun onFling(
        startX: Float, startY: Float, velocityX: Float,
        velocityY: Float
    )

    fun onScale(scaleFactor: Float, focusX: Float, focusY: Float)
    fun onScale(scaleFactor: Float, focusX: Float, focusY: Float, dx: Float, dy: Float)
}