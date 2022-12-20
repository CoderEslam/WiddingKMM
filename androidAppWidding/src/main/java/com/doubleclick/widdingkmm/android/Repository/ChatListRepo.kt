package com.doubleclick.widdingkmm.android.Repository

import android.util.Log
import com.doubleclick.widdingkmm.android.Model.ChatList
import com.doubleclick.widdingkmm.android.Model.Constants
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.`interface`.IChatList
import com.doubleclick.widdingkmm.android.Repository.BaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

/**
 * Created By Eslam Ghazy on 7/16/2022
 */

private const val TAG = "ChatListRepo"

class ChatListRepo(private var iChatList: IChatList) : BaseRepository() {

    private var list: MutableList<User> = ArrayList();

    fun getChatList() {
        try {
            reference.child(Constants.DBChatList).child(id).orderByChild("time")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        list.clear();
                        for (snap in snapshot.children) {
                            val gson = Gson()
                            val json = gson.toJson(snap.value)
                            val chatList = gson.fromJson(json, ChatList::class.java) as ChatList
                            if (chatList.id != "") {
                                reference.child(Constants.DBUsers).child(chatList.id)
                                    .addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(snapshotUser: DataSnapshot) {
                                            val gson = Gson()
                                            val json = gson.toJson(snapshotUser.value)
                                            val user = gson.fromJson(json, User::class.java) as User
                                            val u = User(
                                                user.id,
                                                user.name,
                                                user.phone ?: "",
                                                user.email,
                                                user.token,
                                                user.image ?: "",
                                                user.description ?: "",
                                                user.cover ?: ""
                                            )
                                            list.add(u);
                                            iChatList.getUserAdd(u);
                                            iChatList.getChatList(list)
                                        }

                                        override fun onCancelled(error: DatabaseError) {

                                        }

                                    })
                            }

                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        } catch (e: NullPointerException) {
            Log.e(TAG, "getChatList: ${e.message}")
        }

    }

}