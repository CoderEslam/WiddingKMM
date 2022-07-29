/*
package com.doubleclick.widdingkmm.android.Database

import androidx.lifecycle.LiveData
import com.doubleclick.widdingkmm.android.Model.MessageModel
import retrofit2.http.Query

*/
/**
 * Created By Eslam Ghazy on 7/15/2022
 *//*


@Dao
interface ChatDao {
    @Insert
    fun insert(model: MessageModel)

    @Update
    fun update(model: MessageModel)

    @Delete
    fun delete(model: MessageModel)

    @Query("DELETE FROM MessageModel")
    fun  // to delete all data in table
            deleteAllData()

    @Query("SELECT * FROM MessageModel  WHERE ((sender==:myID AND receiver ==:friendID) OR (sender==:friendID AND receiver ==:myID)) ORDER BY time ASC")
    fun getAllChat(myID: String, friendID: String): LiveData<List<MessageModel>>

    @Query("SELECT * FROM MessageModel  WHERE ((sender==:myID AND receiver == :friendID) OR (sender==:friendID AND receiver ==:myID)) ORDER BY time DESC LIMIT 1")
    fun GetChat(myID: String, friendID: String): LiveData<List<MessageModel>>

    @Query("SELECT * FROM MessageModel  WHERE (((sender==:myID AND receiver == :friendID) OR (sender==:friendID AND receiver ==:myID))) ORDER BY time ASC")
    fun getList(myID: String, friendID: String): List<MessageModel>

    //    @Query("SELECT * FROM Chat  WHERE (sender ==:friendID AND receiver == :myID) ORDER BY date DESC LIMIT 1")
    //    LiveData<Chat> getLastMassege(String friendID, String myID);
    @Query("SELECT * FROM MessageModel  WHERE ((sender==:myID AND receiver == :friendID) OR (sender==:friendID AND receiver ==:myID)) ORDER BY time DESC LIMIT 1")
    fun getLastRowMessage(friendID: String, myID: String): LiveData<MessageModel>

    @Query("SELECT * FROM MessageModel  WHERE ((sender==:myID AND receiver == :friendID AND message =:reply) OR (sender==:friendID AND receiver ==:myID AND message=:reply)) ORDER BY time DESC LIMIT 1")
    fun getIndexOfObject(myID: String, friendID: String, reply: String): MessageModel

    @Query("SELECT * FROM MessageModel")
    fun getAllData(): LiveData<List<MessageModel>>
}
*/
