package com.doubleclick.widdingkmm.android.ViewHolder

import android.view.View
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewHolder.BaseViewHolder
import com.doubleclick.widdingkmm.android.Views.ImageSlider.constants.ActionTypes
import com.doubleclick.widdingkmm.android.Views.ImageSlider.interfaces.ItemClickListener
import com.doubleclick.widdingkmm.android.Views.ImageSlider.interfaces.TouchListener
import com.doubleclick.widdingkmm.android.Views.ImageSlider.models.SlideModel
import com.doubleclick.widdingkmm.android.Views.ImageSlider.ImageSlider

/**
 * Created By Eslam Ghazy on 7/5/2022
 */
class AdvertisementViewHolder(itemView: View) : BaseViewHolder(itemView) {
    private var imageSlider: ImageSlider =
        itemView.findViewById(R.id.imageSlider);

    fun setTrademark(advertisements: ArrayList<SlideModel>) {
        imageSlider.setImageList(advertisements)
        imageSlider.startSliding(4000)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {

            }
        })
        imageSlider.setTouchListener(object : TouchListener {
            override fun onTouched(touched: ActionTypes) {

            }
        })
    }


}