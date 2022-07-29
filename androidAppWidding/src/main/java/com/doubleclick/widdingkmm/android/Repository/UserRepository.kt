package com.doubleclick.widdingkmm.android.Repository

import android.util.Log
import com.doubleclick.widdingkmm.android.Model.Constants
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.`interface`.userinterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

/**
 * Created By Eslam Ghazy on 7/8/2022
 */
class UserRepository : BaseRepository {

    public lateinit var userinter: userinterface

    constructor(userinter: userinterface) : super() {
        this.userinter = userinter
    }

    fun getUserDate() {
        reference.child(Constants.DBUsers)
            .child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val gson = Gson()
                        val json = Gson().toJson(snapshot.value)
                        val data = gson.fromJson(json, User::class.java)
                        userinter.let {
                            if (data != null) {
                                it.userData(
                                    User(
                                        data.id,
                                        data.name,
                                        data.phone,
                                        data.email,
                                        data.token,
                                        data.image,
                                        data.description,
                                        data.cover
                                    )
                                )
                            }
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun getInfoUserById(id: String?) {
        try {
            reference.child(Constants.DBUsers.toString()).child(id!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        try {
                            if (snapshot.exists()) {
                                val gson = Gson()
                                val json = Gson().toJson(snapshot.value)
                                val data = gson.fromJson(json, User::class.java)
                                userinter.friendData(data!!)
                            }
                        } catch (e: Exception) {
                            Log.e("Exception", e.message!!)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        } catch (ignored: DatabaseException) {
        }
    }

}