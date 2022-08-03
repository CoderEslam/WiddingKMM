package com.doubleclick.widdingkmm.android.ViewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.doubleclick.widdingkmm.android.Views.ExoMedia.listener.OnCompletionListener
import com.doubleclick.widdingkmm.android.Views.ExoMedia.ui.widget.VideoView
import com.doubleclick.widdingkmm.android.`interface`.OnMessageClick
import com.doubleclick.widdingkmm.android.ui.ViewImageVideo.ListenRecord_VideoActivity
import java.text.SimpleDateFormat

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
class VoiceViewHolder(itemView: View, onMessageClick: OnMessageClick, myId: String) :
    BaseViewHolder(itemView) {
    private val playVoice: ImageView
    private var isPlay = true
    private val onMessageClick: OnMessageClick
    private val myId: String
    private val seen: ImageView
    private val time: TextView
    private val seekBar: SeekBar
    private val video: VideoView

    @SuppressLint("UseCompatLoadingForDrawables", "SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun Play(messageModel: MessageModel, position: Int) {
//        time.text = SimpleDateFormat("M/d/yy, h:mm a").format(messageModel.time.toLong()).toString()
        if (messageModel.message != "") {
            if (messageModel.receiver == myId) {
                seen.visibility = View.INVISIBLE
                video.setVideoURI(Uri.parse(messageModel.message));
                playVoice.setOnClickListener {
                    /*if (isPlay) {
                        playVoice.setImageDrawable(
                            itemView.resources.getDrawable(R.drawable.pause)
                        )
                        video.start()
                        isPlay = false
                        OnPreparedListener(video.duration, video.currentPosition)
                    } else {
                        playVoice.setImageDrawable(
                            itemView.resources.getDrawable(R.drawable.play)
                        )
                        video.stopPlayback()
                        isPlay = true
                    }*/
                    val intent = Intent(itemView.context, ListenRecord_VideoActivity::class.java);
                    intent.putExtra("url", messageModel.message);
                    itemView.context.startActivity(intent);
                }

            } else {
                seen.setImageDrawable(
                    if (messageModel.seen == "true") itemView.context.resources.getDrawable(
                        R.drawable.done_all
                    ) else itemView.context.resources.getDrawable(R.drawable.done)
                )
            }
//            playVoice.setImageDrawable(itemView.context.resources.getDrawable(R.drawable._download_))

        }
        itemView.setOnClickListener { v ->
            val popupMenu =
                PopupMenu(itemView.context, v)
            popupMenu.menuInflater.inflate(R.menu.text_chat_option, popupMenu.menu)
            popupMenu.menu.findItem(R.id.copy).title = "download"
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.deleteForme) {
                    onMessageClick.deleteForMe(messageModel, position)
                    return@setOnMenuItemClickListener true
                } else if (item.itemId == R.id.deleteforeveryone) {
                    if (isNetworkConnected(itemView.context)) {
                        onMessageClick.deleteForAll(messageModel, position)
                    } else {
                        Toast.makeText(
                            itemView.context,
                            "No Internet Connection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@setOnMenuItemClickListener true
                } else if (R.id.copy == item.itemId) {
                    onMessageClick.download(messageModel, adapterPosition)
                    return@setOnMenuItemClickListener true
                } else {
                    return@setOnMenuItemClickListener false
                }
            }
            popupMenu.show()
        }

        video.setOnCompletionListener(object : OnCompletionListener {
            override fun onCompletion() {
                playVoice.setImageDrawable(itemView.resources.getDrawable(R.drawable.play))
                isPlay = true
            }
        })
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun OnPreparedListener(duration: Long, currentPosition: Long) {
        Thread {
            do {
                try {
                    seekBar.setProgress((currentPosition * 100 / duration).toInt(), true)
                } catch (ignored: Exception) {
                }
            } while (seekBar.progress <= 100)
        }.start()
    }

    init {
        this.onMessageClick = onMessageClick
        this.myId = myId
        playVoice = itemView.findViewById(R.id.playVoice)
        seen = itemView.findViewById(R.id.seen)
        time = itemView.findViewById(R.id.time)
        seekBar = itemView.findViewById(R.id.seekBar)
        video = itemView.findViewById(R.id.video);
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }
}
