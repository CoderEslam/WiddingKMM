package com.doubleclick.widdingkmm.android.Views.ImageSlider.interfaces

import com.doubleclick.widdingkmm.android.Views.ImageSlider.constants.ActionTypes


interface TouchListener {
    /**
     * Click listener touched item function.
     *
     * @param  touched  slider boolean
     */
    fun onTouched(touched: ActionTypes)
}