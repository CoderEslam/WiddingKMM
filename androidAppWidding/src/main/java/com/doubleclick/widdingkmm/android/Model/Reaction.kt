package com.doubleclick.widdingkmm.android.Model


import java.util.*

/**
 * Created By Eslam Ghazy on 7/19/2022
 */
class Reaction {
    private val reactText: String?
    private val reactType: String?
    private val reactTextColor: String?
    private val reactIconId: Int

    /**
     * This Constructor for default state because React type not equal react Text
     * for example in library default state text is 'like' but type is 'default'
     */
    constructor(reactText: String?, reactType: String?, reactTextColor: String?, reactIconId: Int) {
        this.reactText = reactText
        this.reactType = reactType
        this.reactTextColor = reactTextColor
        this.reactIconId = reactIconId
    }

    /**
     * Constructor for all Reaction that text is equal type
     * for example in like state text is 'like' and type is 'like' also
     */
    constructor(reactText: String?, reactTextColor: String?, reactIconId: Int) {
        this.reactText = reactText
        reactType = reactText
        this.reactTextColor = reactTextColor
        this.reactIconId = reactIconId
    }

    fun getReactText(): String? {
        return reactText
    }

    fun getReactTextColor(): String? {
        return reactTextColor
    }

    fun getReactIconId(): Int {
        return reactIconId
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val reaction = o as Reaction
        return reactIconId == reaction.reactIconId &&
                reactText != null && reactText == reaction.reactText &&
                reactType != null && reactType == reaction.reactType &&
                reactTextColor != null && reactTextColor == reaction.reactTextColor
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(
            intArrayOf(
                reactIconId,
                reactText.hashCode(),
                reactType.hashCode(),
                reactTextColor.hashCode()
            )
        )
    }

    override fun toString(): String {
        return "Reaction{" +
                "reactText='" + reactText + '\'' +
                ", reactType='" + reactType + '\'' +
                ", reactTextColor='" + reactTextColor + '\'' +
                ", reactIconId=" + reactIconId +
                '}'
    }
}
