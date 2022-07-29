package com.doubleclick.widdingkmm.android.Views.socialtextview.widget

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import java.util.regex.Pattern

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
open interface SocialView {
    /**
     * Returns regex that are responsible for finding **hashtags**.
     * By default, the pattern are `#(\w+)`.
     */
    fun getHashtagPattern(): Pattern

    /**
     * Returns regex that are responsible for finding **mentions**.
     * By default, the pattern are `@(\w+)`.
     */
    fun getMentionPattern(): Pattern

    /**
     * Returns regex that are responsible for finding **hyperlinks**.
     * By default, the pattern are [PatternsCompat.WEB_URL].
     */
    fun getHyperlinkPattern(): Pattern

    /**
     * Modify regex that are responsible for finding **hashtags**.
     *
     * @param pattern custom regex. When null, default pattern will be used.
     */
    fun setHashtagPattern(pattern: Pattern?)

    /**
     * Modify regex that are responsible for finding **hashtags**.
     *
     * @param pattern custom regex. When null, default pattern will be used.
     */
    fun setMentionPattern(pattern: Pattern?)

    /**
     * Modify regex that are responsible for finding **hashtags**.
     *
     * @param pattern custom regex. When null, default pattern will be used.
     */
    fun setHyperlinkPattern(pattern: Pattern?)

    /**
     * Returns true if **hashtags** in this view are spanned.
     */
    fun isHashtagEnabled(): Boolean

    /**
     * Returns true if **mentions** in this view are spanned.
     */
    fun isMentionEnabled(): Boolean

    /**
     * Returns true if **hyperlinks** in this view are spanned.
     */
    fun isHyperlinkEnabled(): Boolean

    /**
     * Determine whether this view should span **hashtags**.
     *
     * @param enabled True when spanning should be enabled.
     */
    fun setHashtagEnabled(enabled: Boolean)

    /**
     * Determine whether this view should span **mentions**.
     *
     * @param enabled True when spanning should be enabled.
     */
    fun setMentionEnabled(enabled: Boolean)

    /**
     * Determine whether this view should span **hyperlinks**.
     *
     * @param enabled True when spanning should be enabled.
     */
    fun setHyperlinkEnabled(enabled: Boolean)

    /**
     * Returns color instance of **hashtags**, default is color accent of current app theme.
     * Will still return corresponding color even when [.isHashtagEnabled] is false.
     */
    fun getHashtagColors(): ColorStateList

    /**
     * Returns color instance of **mentions**, default is color accent of current app theme.
     * Will still return corresponding color even when [.isMentionEnabled] ()} is false.
     */
    fun getMentionColors(): ColorStateList

    /**
     * Returns color instance of **hyperlinks**, default is color accent of current app theme.
     * Will still return corresponding color even when [.isHyperlinkEnabled] ()} is false.
     */
    fun getHyperlinkColors(): ColorStateList

    /**
     * Sets **hashtags** color instance.
     *
     * @param colors Colors state list instance.
     */
    fun setHashtagColors(colors: ColorStateList)

    /**
     * Sets **mentions** color instance.
     *
     * @param colors Colors state list instance.
     */
    fun setMentionColors(colors: ColorStateList)

    /**
     * Sets **hyperlinks** color instance.
     *
     * @param colors Colors state list instance.
     */
    fun setHyperlinkColors(colors: ColorStateList)

    /**
     * Returns color integer of **hashtags**.
     *
     * @see .getHashtagColors
     */
    @ColorInt
    fun getHashtagColor(): Int

    /**
     * Returns color integer of **mentions**.
     *
     * @see .getMentionColors
     */
    @ColorInt
    fun getMentionColor(): Int

    /**
     * Returns color integer of **hyperlinks**.
     *
     * @see .getHyperlinkColors
     */
    @ColorInt
    fun getHyperlinkColor(): Int

    /**
     * Sets **hashtags** color integer.
     *
     * @param color Color integer.
     * @see .setHashtagColors
     */
    fun setHashtagColor(@ColorInt color: Int)

    /**
     * Sets **mentions** color integer.
     *
     * @param color Color integer.
     * @see .setMentionColors
     */
    fun setMentionColor(@ColorInt color: Int)

    /**
     * Sets **hyperlinks** color integer.
     *
     * @param color Color integer.
     * @see .setHyperlinkColors
     */
    fun setHyperlinkColor(@ColorInt color: Int)

    /**
     * Registers a callback to be invoked when a **hashtag** is clicked.
     *
     * @param listener The callback that will run.
     */
    fun setOnHashtagClickListener(listener: OnClickListener?)

    /**
     * Registers a callback to be invoked when a **mention** is clicked.
     *
     * @param listener The callback that will run.
     */
    fun setOnMentionClickListener(listener: OnClickListener?)

    /**
     * Registers a callback to be invoked when a **hyperlink** is clicked.
     *
     * @param listener The callback that will run.
     */
    fun setOnHyperlinkClickListener(listener: OnClickListener?)

    /**
     * Registers a text watcher to be invoked when a **hashtag** is modified.
     *
     * @param listener The callback that will run.
     */
    fun setHashtagTextChangedListener(listener: OnChangedListener?)

    /**
     * Registers a text watcher to be invoked when a **mention** is modified.
     *
     * @param listener The callback that will run.
     */
    fun setMentionTextChangedListener(listener: OnChangedListener?)

    /**
     * Returns list of all **hashtags** found in [TextView.getText].
     */
    fun getHashtags(): List<String?>

    /**
     * Returns list of all **mentions** found in [TextView.getText].
     */
    fun getMentions(): List<String?>

    /**
     * Returns list of all **hyperlinks** found in [TextView.getText].
     */
    fun getHyperlinks(): List<String?>

    /**
     * Interface definition for a callback to be invoked when a **hashtag**,
     * **mention**, or **hyperlink** is clicked.
     */
    interface OnClickListener {
        /**
         * Called when a text has been clicked.
         *
         * @param view The view that the texts belong to.
         * @param text The text that was clicked.
         */
        fun onClick(view: SocialView, text: CharSequence)
    }

    /**
     * Interface definition for a callback to be invoked when a **hashtag**,
     * **mention**, or **hyperlink** is modified.
     */
    interface OnChangedListener {
        /**
         * Called when a text has been modified.
         *
         * @param view The view that the texts belong to.
         * @param text The text that was modified.
         */
        fun onChanged(view: SocialView, text: CharSequence)
    }
}