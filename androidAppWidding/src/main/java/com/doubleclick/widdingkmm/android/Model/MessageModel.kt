package com.doubleclick.widdingkmm.android.Model

import android.os.Parcel
import java.io.Serializable

/**
 * Created By Eslam Ghazy on 7/15/2022
 */
open class MessageModel : Serializable {

    var id: String = ""
    var message: String = ""
    var type: String = ""
    var sender: String = ""
    var receiver: String = ""
    var time: String = ""
    var seen: String = ""
    var reply: String = ""
    var uri: String = ""


    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        message = parcel.readString().toString()
        type = parcel.readString().toString()
        sender = parcel.readString().toString()
        receiver = parcel.readString().toString()
        time = parcel.readString().toString()
        seen = parcel.readString().toString()
        reply = parcel.readString().toString()
        uri = parcel.readString().toString()
    }

    fun getListImages(): List<String> {
        return message.replace("[", "").replace("]", "").replace(" ", "").split(",");
    }


    constructor()

    constructor(
        id: String,
        message: String,
        type: String,
        sender: String,
        receiver: String,
        time: String,
        seen: String,
        reply: String,
        uri: String,
    ) {
        this.id = id
        this.message = message
        this.type = type
        this.sender = sender
        this.receiver = receiver
        this.time = time
        this.seen = seen
        this.reply = reply
        this.uri = uri
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MessageModel) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "MessageModel(id='$id', message='$message', type='$type', sender='$sender', receiver='$receiver', time=$time, seen=$seen, reply='$reply', uri='$uri')"
    }


}
