package com.doubleclick.widdingkmm.android.Model


import android.text.TextUtils.split
import java.io.Serializable
import java.util.*

/**
 * Created By Eslam Ghazy on 7/14/2022
 */
data class PostModel(
    val images: String, /* as list*/
    val id: String,
    val userId: String,
    val time: Long,
    val lastModified: Long,
    val caption: String
) : Serializable {


    fun getList(): List<String> {
        return images.replace("[", "").replace("]", "").replace(" ", "").split(",");
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostModel) return false

        if (id != other.id) return false
        if (userId != other.userId) return false

        return true
    }

}