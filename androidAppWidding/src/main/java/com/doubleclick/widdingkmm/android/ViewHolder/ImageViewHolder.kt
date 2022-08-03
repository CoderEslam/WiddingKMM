package com.doubleclick.widdingkmm.android.ViewHolder

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.ablanco.zoomy.Zoomy
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.`interface`.OnMessageClick
import java.text.SimpleDateFormat

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
class ImageViewHolder(
    itemView: View,
    onMessageClick: OnMessageClick,
    activity: Activity,
    myId: String
) :
    BaseViewHolder(itemView) {
    private val imageView: ImageView
    private val optins: ImageView
    private val seen: ImageView
    private val onMessageClick: OnMessageClick
    private val myId: String
    private val time: TextView
    private val activity: Activity;

    @SuppressLint("UseCompatLoadingForDrawables", "SimpleDateFormat")
    fun ShowImage(messageModel: MessageModel, position: Int) {
        val builder: Zoomy.Builder = Zoomy.Builder(activity).target(imageView)
        builder.register()
        time.text = SimpleDateFormat("M/d/yy, h:mm a").format(messageModel.time).toString()
        if (messageModel.uri.toString() != "") {
            /* to check if file exist or not
             * */
            if (messageModel.receiver == myId) {
                seen.visibility = View.INVISIBLE
            } else {
                seen.setImageDrawable(
                    if (messageModel.seen == "true") itemView.context.resources.getDrawable(R.drawable.done_all) else itemView.context.resources.getDrawable(
                        R.drawable.done
                    )
                )
            }

            Glide.with(itemView.context)
                .load(getRealPathFromUri(itemView.context, Uri.parse(messageModel.uri)))
                .into(imageView)
        } else {
            if (messageModel.message.toString() != "") {
                if (messageModel.receiver == myId) {
                    seen.visibility = View.INVISIBLE
                } else {
                    seen.setImageDrawable(
                        if (messageModel.seen=="true") itemView.context.resources.getDrawable(
                            R.drawable.done_all
                        ) else itemView.context.resources.getDrawable(R.drawable.done)
                    )
                }
                Glide.with(itemView.context).load(messageModel.message).into(imageView)
            }
        }
        optins.setOnClickListener { v: View? ->
            val popupMenu =
                PopupMenu(
                    imageView.getContext(),
                    v!!
                )
            popupMenu.menuInflater.inflate(R.menu.menu_chat_image_video, popupMenu.menu)
            if (messageModel.uri.toString() != "") {
                popupMenu.menu.findItem(R.id.download).isVisible = false
            }
            popupMenu.setOnMenuItemClickListener { item ->
                if (R.id.deleteforeveryone === item.itemId) {
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
                if (R.id.deleteForme == item.itemId) {
                    onMessageClick.deleteForMe(messageModel, position)
                }
                if (R.id.download == item.itemId) {
                    onMessageClick.download(messageModel, position)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    companion object {
        fun getRealPathFromUri(context: Context, contentUri: Uri?): String {
            var cursor: Cursor? = null
            return try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                cursor.getString(column_index)
            } finally {
                cursor?.close()
            }
        }
    }

    init {
        this.onMessageClick = onMessageClick
        this.myId = myId
        this.activity = activity;
        imageView = itemView.findViewById(R.id.image)
        optins = itemView.findViewById(R.id.optins)
        seen = itemView.findViewById(R.id.seen)
        time = itemView.findViewById(R.id.time)
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }
}
