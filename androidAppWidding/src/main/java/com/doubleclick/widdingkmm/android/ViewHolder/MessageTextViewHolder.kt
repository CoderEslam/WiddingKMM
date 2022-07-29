package com.doubleclick.widdingkmm.android.ViewHolder

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewHolder.BaseViewHolder
import com.doubleclick.widdingkmm.android.`interface`.OnMessageClick
import java.text.SimpleDateFormat

/**
 * Created By Eslam Ghazy on 7/17/2022
 */
class MessageTextViewHolder(itemView: View, onMessageClick: OnMessageClick, myId: String) :
    BaseViewHolder(itemView) {
    var textMessage: TextView
    var textTime: TextView
    private val seen: ImageView
    private var onMessageClick: OnMessageClick? = null
    private val myId: String
    private val reply: TextView
    private val cardReply: CardView
    private val share: ImageView


    @SuppressLint("SimpleDateFormat", "UseCompatLoadingForDrawables")
    fun SetTextMessage(messageModel: MessageModel, postion: Int) {
        textMessage.text = messageModel.message
        if (messageModel.reply != "") {
            reply.text = messageModel.reply
        } else {
            reply.visibility = View.GONE
            cardReply.visibility = View.GONE
        }
        cardReply.setOnClickListener {
            if (onMessageClick != null) {
                onMessageClick!!.replyIndex(messageModel, postion)
            }
        }
        textTime.text = SimpleDateFormat("M/d/yy, h:mm a").format(messageModel.time).toString()
        if (messageModel.receiver == myId) {
//            seen.visibility = View.INVISIBLE
        } else {
            seen.setImageDrawable(
                if (messageModel.seen) itemView.context.resources.getDrawable(R.drawable.done_all) else itemView.context.resources.getDrawable(
                    R.drawable.done
                )
            )
        }
        share.setOnClickListener {
//            val intent = Intent(itemView.context, ChatListActivity::class.java);
//            intent.putExtra("objectChatShare", messageModel)
//            itemView.context.startActivity(intent);
        }
        itemView.setOnClickListener { v ->
            if (onMessageClick != null) {
                val popupMenu =
                    PopupMenu(itemView.context, v)
                popupMenu.menuInflater.inflate(R.menu.text_chat_option, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    if (item.itemId == R.id.deleteForme) {
                        onMessageClick!!.deleteForMe(messageModel, postion)
                        return@setOnMenuItemClickListener true
                    } else if (item.itemId == R.id.deleteforeveryone) {
                        if (isNetworkConnected(itemView.context)) {
                            onMessageClick!!.deleteForAll(messageModel, postion)
                        } else {
                            Toast.makeText(
                                itemView.context,
                                "No Internet Connection",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        return@setOnMenuItemClickListener true
                    } else if (item.itemId == R.id.copy) {
                        val clipboardManager =
                            itemView.context
                                .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboardManager.text = textMessage.text
                        Toast.makeText(
                            itemView.context,
                            itemView.resources.getString(R.string.text_copied),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnMenuItemClickListener true
                    } else {
                        return@setOnMenuItemClickListener false
                    }
                }
                popupMenu.show()
            }
        }
    }

    init {
        this.onMessageClick = onMessageClick
        this.myId = myId
        textMessage = itemView.findViewById(R.id.textMessage)
        textTime = itemView.findViewById(R.id.textTime)
        seen = itemView.findViewById(R.id.seen)
        reply = itemView.findViewById(R.id.reply)
        cardReply = itemView.findViewById(R.id.cardReply)
        share = itemView.findViewById(R.id.share);
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }
}