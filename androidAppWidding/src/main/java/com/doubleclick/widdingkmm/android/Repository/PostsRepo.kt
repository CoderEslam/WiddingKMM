package com.doubleclick.widdingkmm.android.Repository

import com.doubleclick.widdingkmm.android.Model.Constants
import com.doubleclick.widdingkmm.android.Model.PostModel
import com.doubleclick.widdingkmm.android.Model.PostModelData
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.`interface`.IPosts
import com.doubleclick.widdingkmm.android.Repository.BaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

/**
 * Created By Eslam Ghazy on 7/14/2022
 */
class PostsRepo(private var iPosts: IPosts) : BaseRepository() {

    private val list: MutableList<PostModelData> = ArrayList();
    private val listMyPost: MutableList<PostModelData> = ArrayList();

    fun getPost() {
        reference.child(Constants.DBPosts)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {
                        val gson = Gson()
                        val json = Gson().toJson(snap.value)
                        val postModel = gson.fromJson(json, PostModel::class.java)
                        reference.child(Constants.DBUsers).child(postModel.userId)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val gson = Gson()
                                    val json = Gson().toJson(snapshot.value)
                                    val user = gson.fromJson(json, User::class.java)
                                    list.add(
                                        PostModelData(
                                            User(
                                                user.id,
                                                user.name,
                                                user.phone ?: "",
                                                user.email,
                                                user.token,
                                                user.image ?: "",
                                                user.description ?: "",
                                                user.cover ?: ""
                                            ), PostModel(
                                                postModel.images,
                                                postModel.id,
                                                postModel.userId,
                                                postModel.time,
                                                postModel.lastModified,
                                                postModel.caption ?: ""
                                            )
                                        )
                                    )
                                    iPosts.ListPost(list);
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }

                            })
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    fun getMyPost(id: String) {
        reference.child(Constants.DBPosts).orderByChild("userId").equalTo(id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {
                        val gson = Gson()
                        val json = Gson().toJson(snap.value)
                        val postModel = gson.fromJson(json, PostModel::class.java)
                        reference.child(Constants.DBUsers).child(postModel.userId)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val gson = Gson()
                                    val json = Gson().toJson(snapshot.value)
                                    val user = gson.fromJson(json, User::class.java)
                                    listMyPost.add(
                                        PostModelData(
                                            User(
                                                user.id,
                                                user.name,
                                                user.phone ?: "",
                                                user.email,
                                                user.token,
                                                user.image ?: "",
                                                user.description ?: "",
                                                user.cover ?: ""
                                            ), PostModel(
                                                postModel.images,
                                                postModel.id,
                                                postModel.userId,
                                                postModel.time,
                                                postModel.lastModified,
                                                postModel.caption ?: ""
                                            )
                                        )
                                    )
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }

                            })
                    }
                    iPosts.ListMyPost(listMyPost);
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }


}