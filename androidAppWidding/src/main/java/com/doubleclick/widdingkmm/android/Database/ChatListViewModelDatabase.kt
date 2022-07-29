/*
package com.doubleclick.widdingkmm.android.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.Model.User

*/
/*
 * Created By Eslam Ghazy on 7/15/2022
*//*


class ChatListViewModelDatabase(application: Application) :
    AndroidViewModel(application) {
    private lateinit var mRepositry: ChatListDatabaseRepository

    //    private val mAllChatList: LiveData<List<ChatList>>
    private var userList: List<User>? = null

    //    private val chatListData: LiveData<List<ChatListData>>
    private lateinit var mAllChats: LiveData<List<MessageModel>>


    init {
        try {
            mRepositry = ChatListDatabaseRepository(application)
//        mAllChatList = mRepositry.getAllChatList()
            userList = mRepositry.getUserList()!!
//        chatListData = mRepositry.getChatListData()
            mAllChats = mRepositry.getAllChat()!!

        } catch (e: NullPointerException) {

        }

    }

    ////////////////////////////////////////CHATLIST//////////////////////////////////////////////
//    fun insertChatList(chatList: ChatList?) {
//        mRepositry.insertChatList(chatList)
//    }

//    fun updateChatList(chatList: ChatList?) {
//        mRepositry.updateChatList(chatList)
//    }
//
//    fun deleteChatList(chatList: ChatList?) {
//        mRepositry.deleteChatList(chatList)
//    }

//    fun deleteAllChatList() {
//        mRepositry.deleteAllUsers()
//    }

//    fun getChatListData(): LiveData<List<ChatListData>> {
//        return chatListData
//    }


//    fun getAllChatList(): LiveData<List<ChatList>> {
//        return mAllChatList
//    }

    ///////////////////////////////////////////////////CHATLIST////////////////////////////////////


    ///////////////////////////////////////////USER////////////////////////////////////////////////
    fun insertUser(user: User) {
        mRepositry.insertUser(user)
    }

    fun updateUser(user: User) { //done
        mRepositry.updateUser(user)
    }

    fun deleteUser(user: User) {
        mRepositry.deleteUser(user)
    }

    fun getUserById(id: String): LiveData<User> {
        return mRepositry.getUserById(id)
    }

//    fun deleteAllUser() {
//        mRepositry.deleteAllUsers()
//    }

    fun getUserList(): List<User> {
        return userList!!
    }
    /////////////////////////////////////////USER/////////////////////////////////////////////////////


    //    ====================================Massage===================================================

    fun getAllData(): LiveData<List<MessageModel>> {
        return mRepositry!!.getAllData()!!
    }

    fun getAllData(myID: String, FriendID: String) {
        mRepositry.Load(myID, FriendID)
    }

    fun insert(chat: MessageModel?) {
        mRepositry.insert(chat)
    }

    fun update(chat: MessageModel?) { //done
        mRepositry.update(chat)
    }

    fun delete(chat: MessageModel?) {
        mRepositry.delete(chat)
    }


    fun deleteAll() {
        mRepositry.deleteAllChats()
    }

    fun getLasRowMassage(id: String, myId: String): LiveData<MessageModel> {
        return mRepositry.getLastRowMessage(myId, id)
    }

    fun getIndexOfObject(myID: String, friendID: String, reply: String): MessageModel? {
        return mRepositry.getIndexOfObject(myID, friendID, reply)
    }

    fun getAllChats(): LiveData<List<MessageModel>> {
        return mAllChats
    }

    fun getListData(myID: String, FirendID: String): Collection<MessageModel> {
        return mRepositry.getListData(myID, FirendID)
    }
    //    ====================================Massage===================================================
}
*/
