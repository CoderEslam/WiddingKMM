package com.doubleclick.widdingkmm.android.Views.fabfilter.utils.ArcAnimator

import android.view.View

/**
 * Created By Eslam Ghazy on 12/20/2022
 */
object ArcUtils {
    fun sin(degree: Double): Float {
        return Math.sin(Math.toRadians(degree)).toFloat()
    }

    fun cos(degree: Double): Float {
        return Math.cos(Math.toRadians(degree)).toFloat()
    }

    fun asin(value: Double): Float {
        return Math.toDegrees(Math.asin(value)).toFloat()
    }

    fun acos(value: Double): Float {
        return Math.toDegrees(Math.acos(value)).toFloat()
    }

    fun centerX(view: View): Float {
        return view.x + view.width / 2f
    }

    fun centerY(view: View): Float {
        return view.y + view.height / 2f
    }
}
