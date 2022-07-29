package com.doubleclick.widdingkmm.android.Model


import com.doubleclick.widdingkmm.android.R

/**
 * Created By Eslam Ghazy on 7/19/2022
 */
object FbReactions {
    var defaultReact = Reaction(
        Constants.LIKE,
        Constants.DEFAULT,
        Constants.BLUE,
        R.drawable.ic_like
    )

    fun Reactions(): ArrayList<Reaction> {
        val reactions = ArrayList<Reaction>()
        reactions.add(Reaction(Constants.LIKE, Constants.BLUE, R.drawable.ic_like))
        reactions.add(Reaction(Constants.LOVE, Constants.RED_LOVE, R.drawable.ic_heart))
        reactions.add(
            Reaction(
                Constants.SMILE,
                Constants.YELLOW_WOW,
                R.drawable.ic_happy
            )
        )
        reactions.add(
            Reaction(
                Constants.WOW,
                Constants.YELLOW_WOW,
                R.drawable.ic_surprise
            )
        )
        reactions.add(Reaction(Constants.SAD, Constants.YELLOW_HAHA, R.drawable.ic_sad))
        reactions.add(Reaction(Constants.ANGRY, Constants.RED_ANGRY, R.drawable.ic_angry))
        return reactions
    }
}