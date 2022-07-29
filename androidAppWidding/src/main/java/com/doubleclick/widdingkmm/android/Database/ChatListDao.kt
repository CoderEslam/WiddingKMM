/*
package com.doubleclick.widdingkmm.android.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doubleclick.widdingkmm.android.Model.ChatList
import com.doubleclick.widdingkmm.android.Model.ChatListData
import retrofit2.http.Query

*
 * Created By Eslam Ghazy on 7/15/2022


@Dao
open interface ChatListDao {
    @Insert
    fun insert(chatList: ChatList?)

    @Update
    fun update(chatList: ChatList?)

    @Delete
    fun delete(chatList: ChatList?)

    @Query("DELETE FROM ChatList")
    fun  // to delete all data in table
            deleteAllData()

    @Query("SELECT * FROM ChatList  ORDER BY time ASC")
    fun getChatList(): LiveData<List<ChatList>>

    @Query("SELECT * FROM ChatList WHERE id==:id")
    fun getUserById(id: String?): LiveData<ChatList>

    @Query("SELECT * FROM ChatList  inner join User on User.id = ChatList.id order by ChatList.time  LIMIT 1")
    fun Limitation(): LiveData<ChatListData>

    @Query("SELECT * FROM ChatList  inner join User on User.id = ChatList.id order by ChatList.time ")
    @Transaction
    fun getChatListWithUser(): LiveData<List<ChatListData>>
}
*/
