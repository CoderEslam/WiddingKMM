package com.doubleclick.widdingkmm.android.`interface`

import com.doubleclick.widdingkmm.android.Model.MessageModel

/**
 * Created By Eslam Ghazy on 7/17/2022
 */
interface OnMessageClick {

    fun download(messageModel: MessageModel, pos: Int);

    fun deleteForMe(messageModel: MessageModel, pos: Int)

    fun deleteForAll(messageModel: MessageModel, pos: Int)

    fun replyIndex(messageModel: MessageModel, pos: Int)

}