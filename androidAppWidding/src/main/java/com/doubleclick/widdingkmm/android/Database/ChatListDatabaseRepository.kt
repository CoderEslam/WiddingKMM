/*
package com.doubleclick.widdingkmm.android.Database

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.Model.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

*/
/**
 * Created By Eslam Ghazy on 7/15/2022
 *//*


class ChatListDatabaseRepository {
    //    private val chatListDao: ChatListDao?
    private val userDao: UserDao?
    private var chatDao: ChatDao?

    //    private val getAllChatList: LiveData<List<ChatList>>
    private var userList: List<User>? = null

    //    private val chatListData: LiveData<List<ChatListData>>
//    private val Limitation: LiveData<ChatListData>
    private var getAllChat: LiveData<List<MessageModel>>? = null


    constructor(application: Application?) {
        val db = MessageDatabase.getInstance(application!!)
//        chatListDao = db.EntitiesChatListDAO()
        userDao = db.EntitiesUserDaoDAO()
        chatDao = db.EntitiesDAO()!!
//        getAllChatList = chatListDao!!.getChatList()
        userList = userDao!!.getUserList()
//        chatListData = chatListDao.getChatListWithUser()
//        Limitation = chatListDao.Limitation()
    }

    init {

    }

    ///////////////////////////////////ChatList///////////////////////////////////////
    //insert
//    fun insertChatList(chatList: ChatList?) {
//        InsertChatListAsyncTask(chatListDao).execute(chatList)
//    }

    //delete
//    fun deleteChatList(chatList: ChatList?) {
//        DeleteChatListAsyncTask(chatListDao).execute(chatList)
//    }

    //update
//    fun updateChatList(chatList: ChatList?) { //done
//        UpdatetChatListAsyncTask(chatListDao).execute(chatList)
//    }

//    fun getAllChatList(): LiveData<List<ChatList>> {
//        return getAllChatList
//    }

//    fun getChatListData(): LiveData<List<ChatListData>> {
//        return chatListData
//    }

//    fun getLimitation(): LiveData<ChatListData> {
//        return Limitation
//    }

//    fun deleteAllChatList() {
//        DeleteAllChatListAsyncTask(chatListDao).execute()
//    }

//    private class InsertChatListAsyncTask(private val chatListDao: ChatListDao?) :
//        AsyncTask<ChatList?, Void?, Void?>() {
//        override fun doInBackground(vararg chatLists: ChatList?): Void? {
//            try {
//                chatListDao!!.insert(chatLists[0])
//            } catch (e: Exception) {
//                Log.e("ExceptionInsert", e.message!!)
//            }
//            return null
//        }
//    }

//    private class DeleteChatListAsyncTask(private val chatListDao: ChatListDao?) :
//        AsyncTask<ChatList?, Void?, Void?>() {
//        override fun doInBackground(vararg chatLists: ChatList?): Void? {
//            try {
//                chatListDao!!.delete(chatLists[0])
//            } catch (e: Exception) {
//                Log.e("ExceptionDelete", e.message!!)
//            }
//            return null
//        }
//    }

//    private class UpdatetChatListAsyncTask(private val chatListDao: ChatListDao?) :
//        AsyncTask<ChatList?, Void?, Void?>() {
//        override fun doInBackground(vararg chatLists: ChatList?): Void? {
//            try {
//                chatListDao!!.update(chatLists[0])
//            } catch (e: Exception) {
//                Log.e("ExceptionUpdate", e.message!!)
//            }
//            return null
//        }
//    }

//    private class DeleteAllChatListAsyncTask(private val chatListDao: ChatListDao?) :
//        AsyncTask<Void?, Void?, Void?>() {
//        override fun doInBackground(vararg voids: Void?): Void? {
//            try {
//                chatListDao!!.deleteAllData()
//            } catch (e: Exception) {
//                Log.e("ExceptionDeleteAll", e.message!!)
//            }
//            return null
//        }
//    }

    ///////////////////////////////////ChatList///////////////////////////////////////
    /////////////////////////////////////////USER///////////////////////////
//    insert
    fun insertUser(user: User?) {
        InsertUserAsyncTask(userDao).insert(user!!)
    }

    //    delete
    fun deleteUser(user: User?) {
        DeleteUserAsyncTask(userDao!!).execute(user)
    }

    fun getUserById(id: String?): LiveData<User> {
        return userDao!!.getUserById(id!!)
    }

    //    update
    fun updateUser(user: User) { //done
        UpdatetUserAsyncTask(userDao!!).execute(user)
    }

    fun getUserList(): List<User> {
        return userList!!
    }

    //    deleteAllWords
    fun deleteAllUsers() {
        DeleteAllUserAsyncTask(userDao!!).execute()
    }

    private class InsertUserAsyncTask(private val userDao: UserDao?) {

        //        : AsyncTask<User?, Void?, Void?>() {
//        override fun doInBackground(vararg users: User?): Void? {
        @OptIn(DelicateCoroutinesApi::class)
        fun insert(user: User) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    userDao!!.insert(user!!)
                } catch (e: Exception) {
                    Log.e("ExceptionInsert", e.message!!)
                }
            }

        }

//            return null
//        }
    }

    private class DeleteUserAsyncTask(private val userDao: UserDao) :
        AsyncTask<User?, Void?, Void?>() {
        override fun doInBackground(vararg users: User?): Void? {
            try {
                userDao!!.delete(users[0]!!)
            } catch (e: Exception) {
                Log.e("ExceptionDelete", e.message!!)
            }
            return null
        }
    }

    private class UpdatetUserAsyncTask(private val userDao: UserDao) :
        AsyncTask<User?, Void?, Void?>() {
        override fun doInBackground(vararg users: User?): Void? {
            try {
                userDao!!.update(users[0]!!)
            } catch (e: Exception) {
                Log.e("ExceptionUpdate", e.message!!)
            }
            return null
        }
    }

    private class DeleteAllUserAsyncTask(private val userDao: UserDao) :
        AsyncTask<Void?, Void?, Void?>() { ////////////////////////////////////////USER////////////////////////////
        override fun doInBackground(vararg voids: Void?): Void? {
            try {
                userDao!!.deleteAllData()
            } catch (e: Exception) {
                Log.e("ExceptionDeleteAll", e.message!!)
            }
            return null
        }
    }
//    ============================================MESSAGE=======================================


    fun getAllData(): LiveData<List<MessageModel>> {
        return chatDao!!.getAllData()!!
    }

    fun Load(myID: String, FriendID: String) {
        getAllChat = chatDao!!.getAllChat(myID!!, FriendID!!)!!
    }


    //insert
    fun insert(messageModel: MessageModel?) {
        InsertAsyncTask(chatDao!!).execute(messageModel)
    }

    fun getLastRowMessage(myID: String, FriendID: String): LiveData<MessageModel> {
        return chatDao!!.getLastRowMessage(FriendID!!, myID!!)
    }

    fun getIndexOfObject(myID: String, friendID: String, reply: String): MessageModel? {
        return chatDao!!.getIndexOfObject(myID!!, friendID!!, reply!!)
    }

    fun getListData(myID: String, FriendID: String): Collection<MessageModel> {
        return chatDao!!.getList(myID!!, FriendID!!)
    }

    //delete
    fun delete(messageModel: MessageModel?) {
        DeleteAsyncTask(chatDao!!).execute(messageModel)
    }

    //update
    fun update(messageModel: MessageModel?) { //done
        UpdatetAsyncTask(chatDao!!).execute(messageModel)
    }

    //getAllWords
    fun getAllChat(): LiveData<List<MessageModel>> {
        return getAllChat!!
    }

    //deleteAllWords
    fun deleteAllChats() {
        DeleteAllAsyncTask(chatDao!!).execute()
    }


    private class InsertAsyncTask(private val EntitiesDAO: ChatDao) :
        AsyncTask<MessageModel?, Void?, Void?>() {
        override fun doInBackground(vararg messageModel: MessageModel?): Void? {
            try {
                EntitiesDAO.insert(messageModel[0]!!)
            } catch (e: java.lang.Exception) {
                Log.e("ExceptionInsert", e.message!!)
            }
            return null
        }
    }

    private class DeleteAsyncTask(private val EntitiesDAO: ChatDao) :
        AsyncTask<MessageModel?, Void?, Void?>() {
        override fun doInBackground(vararg messageModel: MessageModel?): Void? {
            try {
                EntitiesDAO.delete(messageModel[0]!!)
            } catch (e: java.lang.Exception) {
                Log.e("ExceptionDelete", e.message!!)
            }
            return null
        }
    }

    private class UpdatetAsyncTask(private val EntitiesDAO: ChatDao) :
        AsyncTask<MessageModel?, Void?, Void?>() {
        override fun doInBackground(vararg messageModel: MessageModel?): Void? {
            try {
                EntitiesDAO.update(messageModel[0]!!)
            } catch (e: java.lang.Exception) {
                Log.e("ExceptionUpdate", e.message!!)
            }
            return null
        }
    }

    private class DeleteAllAsyncTask(private val chatDao: ChatDao) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg voids: Void?): Void? {
            try {
                chatDao.deleteAllData()
            } catch (e: java.lang.Exception) {
                Log.e("ExceptionDeleteAll", e.message!!)
            }
            return null
        }
    }

//    ============================================MESSAGE=======================================


}
*/
