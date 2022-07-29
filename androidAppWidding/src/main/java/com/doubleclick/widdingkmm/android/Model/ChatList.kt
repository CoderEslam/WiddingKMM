package com.doubleclick.widdingkmm.android.Model


/**
 * Created By Eslam Ghazy on 7/14/2022
 */
data class ChatList(
    val id: String,
    val time: Long
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChatList) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "ChatList(id='$id', time=$time)"
    }
}
