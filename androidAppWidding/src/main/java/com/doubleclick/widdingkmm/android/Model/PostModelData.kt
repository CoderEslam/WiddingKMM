package com.doubleclick.widdingkmm.android.Model


import java.io.Serializable

/**
 * Created By Eslam Ghazy on 7/14/2022
 */
data class PostModelData(val user: User, val postModel: PostModel) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostModelData) return false

        if (user != other.user) return false
        if (postModel != other.postModel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = user.hashCode()
        result = 31 * result + postModel.hashCode()
        return result
    }

    override fun toString(): String {
        return "PostModelData(user=$user, postModel=$postModel)"
    }
}
