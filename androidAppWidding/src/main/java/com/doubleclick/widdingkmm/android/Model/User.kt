package com.doubleclick.widdingkmm.android.Model

import java.io.Serializable

/**
 * Created By Eslam Ghazy on 7/7/2022
 */
data class User(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val token: String,
    val image: String,
    val description: String,
    val cover: String
) :Serializable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        if (id != other.id) return false
        if (email != other.email) return false

        return true
    }


    override fun toString(): String {
        return "User(id='$id', name='$name', phone='$phone', email='$email', token='$token', image='$image', description='$description', cover='$cover')"
    }

}
