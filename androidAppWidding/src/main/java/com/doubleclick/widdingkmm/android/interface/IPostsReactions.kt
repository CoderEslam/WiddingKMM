package com.doubleclick.widdingkmm.android.`interface`

import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.Model.Reaction

/**
 * Created By Eslam Ghazy on 7/19/2022
 */
interface IPostsReactions {
    fun setReactions(reaction: Reaction, recyclerView: RecyclerView, pos: Int)
}