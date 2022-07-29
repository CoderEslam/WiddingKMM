package com.doubleclick.widdingkmm.android.`interface`

import com.doubleclick.widdingkmm.android.Model.PostModelData

/**
 * Created By Eslam Ghazy on 7/14/2022
 */
interface IPosts {
    fun ListPost(list: List<PostModelData>)
    fun ListMyPost(list: List<PostModelData>)

}