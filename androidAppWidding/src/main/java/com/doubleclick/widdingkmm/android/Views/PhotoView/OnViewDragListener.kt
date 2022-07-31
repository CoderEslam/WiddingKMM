package com.doubleclick.widdings.Views.PhotoView

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
open interface OnViewDragListener {
    /**
     * Callback for when the photo is experiencing a drag event. This cannot be invoked when the
     * user is scaling.
     *
     * @param dx The change of the coordinates in the x-direction
     * @param dy The change of the coordinates in the y-direction
     */
    fun onDrag(dx: Float, dy: Float)
}
