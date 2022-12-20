package com.doubleclick.widdingkmm.android.Views.fabfilter.views

import android.content.Context
import android.util.AttributeSet
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.crystalrangeseekbar.widgets.CrystalRangeSeekbar

class FilterSeekbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CrystalRangeSeekbar(context, attrs, defStyleAttr) {

    override fun getThumbWidth(): Float =  resources.getDimension(R.dimen.thumb_size)
    override fun getThumbHeight(): Float = resources.getDimension(R.dimen.thumb_size)
    override fun getBarHeight(): Float = thumbHeight / 4.5f
}
