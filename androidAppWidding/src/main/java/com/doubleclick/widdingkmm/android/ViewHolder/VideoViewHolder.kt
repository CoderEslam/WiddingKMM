package com.doubleclick.widdingkmm.android.ViewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewHolder.BaseViewHolder
import com.doubleclick.widdingkmm.android.Views.ExoMedia.listener.OnPreparedListener
import com.doubleclick.widdingkmm.android.`interface`.OnMessageClick
import java.text.SimpleDateFormat

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
class VideoViewHolder(itemView: View, onMessageClick: OnMessageClick, myId: String) :
    BaseViewHolder(itemView), OnPreparedListener {
    private val video: VideoView
    private val options: ImageView
    private val onMessageClick: OnMessageClick
    private val seen: ImageView
    private val myId: String
    private val time: TextView

    @SuppressLint("UseCompatLoadingForDrawables", "SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun play(messageModel: MessageModel, position: Int) {
        time.text = SimpleDateFormat("M/d/yy, h:mm a").format(messageModel.time).toString()
        if (messageModel.message != "") {
            video.setVideoURI(Uri.parse(messageModel.message)) //the string of the URL mentioned above
            if (messageModel.receiver == myId) {
                seen.visibility = View.INVISIBLE
            } else {
                seen.setImageDrawable(
                    if (messageModel.seen) itemView.context.resources.getDrawable(R.drawable.done_all) else itemView.context.resources.getDrawable(
                        R.drawable.done
                    )
                )
            }
        } else {
            itemView.visibility = View.GONE
        }
        options.setOnClickListener { v: View? ->
            val popupMenu =
                PopupMenu(
                    itemView.context,
                    v!!
                )
            popupMenu.menuInflater.inflate(R.menu.menu_chat_image_video, popupMenu.menu)
            if (messageModel.uri.toString() != "") {
                popupMenu.menu.findItem(R.id.download).isVisible = false
            }
            popupMenu.setOnMenuItemClickListener { item ->
                val id = item.itemId
                if (R.id.deleteforeveryone == id) {
                    if (isNetworkConnected(itemView.context)) {
                        onMessageClick.deleteForAll(messageModel, position)
                    } else {
                        Toast.makeText(
                            itemView.context,
                            "No Internet Connection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                if (R.id.deleteForme == id) {
                    onMessageClick.deleteForMe(messageModel, position)
                }
                if (R.id.download == id) {
                    onMessageClick.download(messageModel, adapterPosition)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    init {
        this.onMessageClick = onMessageClick
        this.myId = myId
        video = itemView.findViewById(R.id.video)
        options = itemView.findViewById(R.id.options)
        seen = itemView.findViewById(R.id.seen)
        time = itemView.findViewById(R.id.time)
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }


    override fun onPrepared() {
        video.start()
    }
}
