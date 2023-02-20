package com.doubleclick.widdingkmm.android.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.zoomy.Zoomy
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ui.ViewImageVideo.ListenRecord_VideoActivity
import com.doubleclick.widdingkmm.android.ui.ViewImageVideo.ViewImageActivity
import com.github.chrisbanes.photoview.PhotoView

/**
 * Created By Eslam Ghazy on 7/14/2022
 */
class ImagesAdapter(val activity: Activity, val urls: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageRequest: PhotoView = itemView.findViewById(R.id.imagePost);
        val playVideo: ImageView = itemView.findViewById(R.id.playVideo);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val builder: Zoomy.Builder = Zoomy.Builder(activity).target(holder.imageRequest)
        builder.register()
        Glide.with(holder.itemView.context).load(Uri.parse(urls[position]))
            .into(holder.imageRequest);
        val intent = Intent();
        if (urls[position].contains(".mp4")) {
            holder.playVideo.visibility = View.VISIBLE
            holder.playVideo.setOnClickListener {
                intent.setClass(holder.itemView.context, ListenRecord_VideoActivity::class.java)
                intent.putExtra("url", urls[position].toString());
                holder.itemView.context.startActivity(intent);
            }
        } else {
            holder.playVideo.visibility = View.GONE
            holder.imageRequest.setOnClickListener {
                intent.setClass(holder.itemView.context, ViewImageActivity::class.java)
                intent.putExtra("image", urls[position].toString());
                holder.itemView.context.startActivity(intent);
            }
        }
    }

    override fun getItemCount(): Int {
        return urls.size;
    }


}