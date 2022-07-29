package com.doubleclick.widdings.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.ImageSlider.models.SlideModel

/**
 * Created By Eslam Ghazy on 7/5/2022
 */
class AdvertisementAdapter(private var advertisements: ArrayList<SlideModel>) :
    RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder>() {


    class AdvertisementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisementViewHolder {
        return AdvertisementViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.images, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AdvertisementViewHolder, position: Int) {
        advertisements[position].imagePath?.let { holder.image.setImageResource(it) }
    }

    override fun getItemCount(): Int {
        return advertisements.size;
    }
}