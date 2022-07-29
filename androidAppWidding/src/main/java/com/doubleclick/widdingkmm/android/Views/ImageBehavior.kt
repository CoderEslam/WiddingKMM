package com.doubleclick.widdingkmm.android.Views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.doubleclick.widdingkmm.android.R

/**
 * Created By Eslam Ghazy on 7/6/2022
 */
class ImageBehavior(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {
    private val context: Context
    private var startXPosition = 0
    private var startToolbarPosition = 0f
    private var startYPosition = 0
    private var finalYPosition = 0
    private var finalHeight = 0
    private var startHeight = 0
    private var finalXPosition = 0
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is Toolbar
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        maybeInitProperties(child as ImageView, dependency)
        val maxScrollDistance = (startToolbarPosition - getStatusBarHeight()).toInt()
        val expandedPercentageFactor = dependency.y / maxScrollDistance
        val distanceYToSubtract = ((startYPosition - finalYPosition)
                * (1f - expandedPercentageFactor)) + child.getHeight() / 2
        val distanceXToSubtract = ((startXPosition - finalXPosition)
                * (1f - expandedPercentageFactor)) + child.getWidth() / 2
        val heightToSubtract = (startHeight - finalHeight) * (1f - expandedPercentageFactor)
        child.setY(startYPosition - distanceYToSubtract)
        child.setX(startXPosition - distanceXToSubtract)
        val lp = child.getLayoutParams() as CoordinatorLayout.LayoutParams
        lp.width = (startHeight - heightToSubtract).toInt()
        lp.height = (startHeight - heightToSubtract).toInt()
        child.setLayoutParams(lp)
        return true
    }

    @SuppressLint("PrivateResource")
    private fun maybeInitProperties(child: ImageView, dependency: View) {
        if (startYPosition == 0) startYPosition = dependency.y.toInt()
        if (finalYPosition == 0) finalYPosition = dependency.height / 2
        if (startHeight == 0) startHeight = child.height
        if (finalHeight == 0) finalHeight =
            context.resources.getDimensionPixelOffset(R.dimen.image_small_width)
        if (startXPosition == 0) startXPosition = (child.x + child.width / 2).toInt()
        if (finalXPosition == 0) finalXPosition =
            context.resources.getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + finalHeight / 2
        if (startToolbarPosition == 0f) startToolbarPosition = dependency.y + dependency.height / 2
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    init {
        this.context = context
    }
}
