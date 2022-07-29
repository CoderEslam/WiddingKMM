package com.doubleclick.widdingkmm.android.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.`interface`.userinterface
import com.doubleclick.widdingkmm.android.Repository.UserRepository

/**
 * Created By Eslam Ghazy on 7/8/2022
 */
class UserViewModel : ViewModel(), userinterface {

    val mutableLiveData: MutableLiveData<User> = MutableLiveData();
    val mutableLiveDataFriend: MutableLiveData<User> = MutableLiveData();

    val userRepository = UserRepository(this)

    fun getUserById(id: String?): LiveData<User> {
        userRepository.getInfoUserById(id)
        return mutableLiveDataFriend;
    }

    fun getUserDate(): LiveData<User> {
        userRepository.getUserDate()
        return mutableLiveData;
    }

    override fun userData(user: User) {
        mutableLiveData.value = user
    }

    override fun friendData(user: User) {
        mutableLiveDataFriend.value = user;
    }
}