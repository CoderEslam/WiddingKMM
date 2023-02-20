package com.doubleclick.widdingkmm.android.Adapters

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.zoomy.Zoomy
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.R
import com.github.chrisbanes.photoview.PhotoView

/**
 * Created By Eslam Ghazy on 7/14/2022
 */
class ImagesLocalAdapter(val activity: Activity, val urls: List<String>) :
    RecyclerView.Adapter<ImagesLocalAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageRequest: ImageView = itemView.findViewById(R.id.imagePost);
        val video: VideoView = itemView.findViewById(R.id.video);
        var mediaController: MediaController = MediaController(itemView.context);
        fun getFileExtension(uri: Uri, context: Context): String? {
            val contentResolver = context.contentResolver
            val mimeTypeMap = MimeTypeMap.getSingleton()
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_local_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val builder: Zoomy.Builder = Zoomy.Builder(activity).target(holder.imageRequest)
        builder.register()
        holder.mediaController.requestFocus()
        holder.video.setMediaController(holder.mediaController);
        holder.mediaController.setAnchorView(holder.video);

        if (holder.getFileExtension(Uri.parse(urls[position]), holder.itemView.context)
                .toString() == "mp4"
        ) {
            holder.video.visibility = View.VISIBLE
            holder.imageRequest.visibility = View.GONE
            holder.video.start()
            holder.video.setVideoURI(Uri.parse(urls[position]))
        } else {
            Glide.with(holder.itemView.context).load(Uri.parse(urls[position]))
                .into(holder.imageRequest);
            holder.video.visibility = View.GONE
            holder.imageRequest.visibility = View.VISIBLE

        }


    }

    override fun getItemCount(): Int {
        return urls.size;
    }


}