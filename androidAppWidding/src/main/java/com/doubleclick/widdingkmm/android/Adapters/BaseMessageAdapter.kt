package com.doubleclick.widdingkmm.android.Adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.Model.Constants
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewHolder.*
import com.doubleclick.widdingkmm.android.`interface`.OnMessageClick

/**
 * Created By Eslam Ghazy on 7/17/2022
 */
class BaseMessageAdapter(
    val messageModel: List<MessageModel>,
    val onMessageClick: OnMessageClick,
    val activity: Activity,
    val myId: String
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            Constants.VIDEO_RIGHT -> VideoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_video_right, parent, false), onMessageClick, myId
            )
            Constants.VIDEO_LEFT -> VideoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_video_left, parent, false), onMessageClick, myId
            )
            Constants.VOICE_RIGHT -> VoiceViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.play_voice_right, parent, false), onMessageClick, myId
            )
            Constants.VOICE_LEFT -> VoiceViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.play_voice_left, parent, false), onMessageClick, myId
            )
            Constants.TEXT_RIGHT -> MessageTextViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.right_chat, parent, false),
                onMessageClick,
                myId
            )
            Constants.TEXT_LEFT -> MessageTextViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.left_chat, parent, false),
                onMessageClick,
                myId
            )

            Constants.IMAGE_RIGHT -> ImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_right, parent, false),
                onMessageClick,
                activity,
                myId
            )
            Constants.IMAGE_LEFT -> ImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_left, parent, false),
                onMessageClick, activity, myId,
            )
            Constants.LOCATION_LEFT -> LocationViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_location_left, parent, false), onMessageClick, myId
            )
            Constants.LOCATION_RIGHT -> LocationViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_location_right, parent, false), onMessageClick, myId
            )

            Constants.SHARED_POST_RIGHT -> SharedPostViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.shared_post_right, parent, false),
                activity,
                onMessageClick, myId,
            )
            Constants.SHARED_POST_LEFT -> SharedPostViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.shared_post_left, parent, false),
                activity,
                onMessageClick, myId,
            )
            else -> BaseViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.empty_view, parent, false)
            ) // return  view  defulte
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        try {
            when (messageModel[position].type) {
                Constants.VOICE -> (holder as VoiceViewHolder).Play(
                    messageModel[position],
                    position
                )
                Constants.TEXT -> (holder as MessageTextViewHolder).SetTextMessage(
                    messageModel[position],
                    position
                )
                Constants.IMAGE -> (holder as ImageViewHolder).ShowImage(
                    messageModel[position],
                    position
                )
                Constants.VIDEO -> (holder as VideoViewHolder).play(
                    messageModel[position],
                    position
                )
                Constants.LOCATION -> (holder as LocationViewHolder).OpenLocation(
                    messageModel[position],
                    position
                )
                Constants.SHARED_POST -> (holder as SharedPostViewHolder).sharedPost(
                    messageModel[position],
                    position
                )
                else -> {
                    (holder).itemView.visibility = View.VISIBLE
                }
            }
        } catch (e: java.lang.NullPointerException) {
            Log.e("Exception", e.message!!)
        }
    }

    override fun getItemCount(): Int {
        return messageModel.size;
    }

    override fun getItemViewType(position: Int): Int {
        try {
            if (messageModel[position].type != "") {
                if (messageModel[position].type == Constants.VOICE) {
                    return if (messageModel[position].sender == myId) {
                        Constants.VOICE_RIGHT
                    } else {
                        Constants.VOICE_LEFT
                    }
                }
                if (messageModel[position].type == Constants.IMAGE) {
                    return if (messageModel[position].sender == myId) {
                        Constants.IMAGE_RIGHT
                    } else {
                        Constants.IMAGE_LEFT
                    }
                }
                if (messageModel[position].type == Constants.TEXT) {
                    return if (messageModel[position].sender == myId) {
                        Constants.TEXT_RIGHT
                    } else {
                        Constants.TEXT_LEFT
                    }
                }

                if (messageModel[position].type == Constants.LOCATION) {
                    return if (messageModel[position].sender == myId) {
                        Constants.LOCATION_RIGHT
                    } else {
                        Constants.LOCATION_LEFT
                    }
                }
                if (messageModel[position].type == Constants.VIDEO) {
                    return if (messageModel[position].sender == myId) {
                        Constants.VIDEO_RIGHT
                    } else {
                        Constants.VIDEO_LEFT
                    }
                }
                if (messageModel[position].type == Constants.SHARED_POST) {
                    return if (messageModel[position].sender == myId) {
                        Constants.SHARED_POST_RIGHT
                    } else {
                        Constants.SHARED_POST_LEFT
                    }
                }
            } else {
                return Constants.DEFAULT_LAYOUT
            }
        } catch (e: NullPointerException) {
            Log.e("Exception", e.message!!)
        }
        return 0
    }
}