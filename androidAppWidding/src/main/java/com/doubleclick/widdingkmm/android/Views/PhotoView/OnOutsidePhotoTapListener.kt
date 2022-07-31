package com.doubleclick.widdings.Views.PhotoView

import android.widget.ImageView

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
open interface OnOutsidePhotoTapListener {
    /**
     * The outside of the photo has been tapped
     */
    fun onOutsidePhotoTap(imageView: ImageView?)
}
