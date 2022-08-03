package com.doubleclick.widdingkmm.android.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.Repository.ChatListRepo
import com.doubleclick.widdingkmm.android.`interface`.IChatList

/**
 * Created By Eslam Ghazy on 7/21/2022
 */
class ChatListViewModel : ViewModel(), IChatList {


    val mutableLiveData: MutableLiveData<List<User>> = MutableLiveData();

    val mutableLiveDataUserAdd: MutableLiveData<User> = MutableLiveData();


    fun getChatList(): LiveData<List<User>> {
        ChatListRepo(this).getChatList()
        return mutableLiveData
    }

    fun getUserAdd(): LiveData<User> {
        return mutableLiveDataUserAdd
    }

    override fun getChatList(chatList: List<User>) {
        mutableLiveData.value = chatList;
    }

    override fun getUserAdd(user: User) {
        mutableLiveDataUserAdd.value = user
    }
}