package com.doubleclick.widdingkmm.android.Views.socialtextview

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
abstract class TouchableSpan(
    normalTextColor: Int,
    pressedTextColor: Int,
    underlineEnabled: Boolean
) :
    ClickableSpan() {
    private val underlineEnabled: Boolean
    private val pressedTextColor: Int
    private val normalTextColor: Int
    private var pressed = false
    override fun updateDrawState(paint: TextPaint) {
        // Determine whether to paint it pressed or normally
        val textColor = if (pressed) pressedTextColor else normalTextColor
        paint.color = textColor
        paint.isUnderlineText = underlineEnabled
        paint.bgColor = Color.TRANSPARENT
    }

    /**
     * Sets the flag for when the span is pressed.
     * @param value True if pressed
     */
    fun setPressed(value: Boolean) {
        pressed = value
    }

    init {
        this.normalTextColor = normalTextColor
        this.pressedTextColor = pressedTextColor
        this.underlineEnabled = underlineEnabled
    }
}