package com.doubleclick.widdingkmm.android.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleclick.widdingkmm.android.Model.PostModelData
import com.doubleclick.widdingkmm.android.Repository.PostsRepo
import com.doubleclick.widdingkmm.android.`interface`.IPosts

/**
 * Created By Eslam Ghazy on 7/14/2022
 */
class PostsViewModel : ViewModel(), IPosts {

    val postsRepo = PostsRepo(this)
    val mutableLiveData: MutableLiveData<List<PostModelData>> = MutableLiveData();
    val mutableLiveDataMyPost: MutableLiveData<List<PostModelData>> = MutableLiveData();


    fun getLiveMyPosts(id: String): LiveData<List<PostModelData>> {
        postsRepo.getMyPost(id)
        return mutableLiveDataMyPost;
    }

    fun getLiveListData(): LiveData<List<PostModelData>> {
        postsRepo.getPost()
        return mutableLiveData
    }


    override fun ListPost(list: List<PostModelData>) {
        mutableLiveData.value = list;
    }

    override fun ListMyPost(list: List<PostModelData>) {
        mutableLiveDataMyPost.value = list;
    }
}