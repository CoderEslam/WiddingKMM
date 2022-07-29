package com.doubleclick.widdingkmm.android.Model


/**
 * Created By Eslam Ghazy on 7/16/2022
 */
class Data(
    private  var user: String,
    private var icon: Int,
    private  var body: String,
    private  var title: String,
    private var sent: String
) {

    fun getUser(): String {
        return user
    }

    fun setUser(user: String) {
        this.user = user
    }

    fun getIcon(): Int {
        return icon
    }

    fun setIcon(icon: Int) {
        this.icon = icon
    }

    fun getBody(): String {
        return body
    }

    fun setBody(body: String) {
        this.body = body
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getSent(): String {
        return sent
    }

    fun setSent(sent: String) {
        this.sent = sent
    }


}