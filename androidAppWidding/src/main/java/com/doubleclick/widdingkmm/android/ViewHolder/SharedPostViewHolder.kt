package com.doubleclick.widdingkmm.android.ViewHolder

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.socialtextview.SocialTextView
import com.doubleclick.widdingkmm.android.`interface`.OnMessageClick
import com.doubleclick.widdings.Adapters.ImagesAdapter
import java.text.SimpleDateFormat

/**
 * Created By Eslam Ghazy on 7/26/2022
 */
class SharedPostViewHolder(
    itemView: View,
    activity: Activity,
    onMessageClick: OnMessageClick,
    myId: String
) :
    BaseViewHolder(itemView) {

    private val socialTextView: SocialTextView
    private val imagePost: RecyclerView
    private val share: ImageView
    private val activity: Activity;
    private val textTime: TextView
    private val seen: ImageView
    private val myId: String


    fun sharedPost(messageModel: MessageModel, postion: Int) {
        socialTextView.setLinkText(messageModel.reply)
        imagePost.adapter = ImagesAdapter(activity, messageModel.getListImages())
//        textTime.text = SimpleDateFormat("M/d/yy, h:mm a").format(messageModel.time).toString()
        if (messageModel.receiver == myId) {
//            seen.visibility = View.INVISIBLE
        } else {
            seen.setImageDrawable(
                if (messageModel.seen=="true") itemView.context.resources.getDrawable(R.drawable.done_all) else itemView.context.resources.getDrawable(
                    R.drawable.done
                )
            )
        }


    }

    init {
        socialTextView = itemView.findViewById(R.id.socialTextView)
        imagePost = itemView.findViewById(R.id.imagePost);
        share = itemView.findViewById(R.id.share);
        this.activity = activity
        this.myId = myId;
        textTime = itemView.findViewById(R.id.time)
        seen = itemView.findViewById(R.id.seen);
    }

}