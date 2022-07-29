package com.doubleclick.widdingkmm.android.Views.socialtextview.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText
import com.doubleclick.widdingkmm.android.Views.socialtextview.internal.SocialViewHelper
import java.util.regex.Pattern

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
class SocialEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) :
    AppCompatEditText(context!!, attrs, defStyleAttr), SocialView {
    private val helper: SocialView

    /**
     * {@inheritDoc}
     */
    override fun getHashtagPattern(): Pattern {
        return helper.getHashtagPattern()
    }

    /**
     * {@inheritDoc}
     */
    override fun getMentionPattern(): Pattern {
        return helper.getMentionPattern()
    }

    /**
     * {@inheritDoc}
     */
    override fun getHyperlinkPattern(): Pattern {
        return helper.getHyperlinkPattern()
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagPattern(pattern: Pattern?) {
        helper.setHashtagPattern(pattern)
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionPattern(pattern: Pattern?) {
        helper.setMentionPattern(pattern)
    }

    /**
     * {@inheritDoc}
     */
    override fun setHyperlinkPattern(pattern: Pattern?) {
        helper.setHyperlinkPattern(pattern)
    }

    /**
     * {@inheritDoc}
     */
    override fun isHashtagEnabled(): Boolean {
        return helper.isHashtagEnabled()
    }

    /**
     * {@inheritDoc}
     */
    override fun isMentionEnabled(): Boolean {
        return helper.isMentionEnabled()
    }

    /**
     * {@inheritDoc}
     */
    override fun isHyperlinkEnabled(): Boolean {
        return helper.isHyperlinkEnabled()
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagEnabled(enabled: Boolean) {
        helper.setHashtagEnabled(enabled)
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionEnabled(enabled: Boolean) {
        helper.setMentionEnabled(enabled)
    }

    /**
     * {@inheritDoc}
     */
    override fun setHyperlinkEnabled(enabled: Boolean) {
        helper.setHyperlinkEnabled(enabled)
    }

    /**
     * {@inheritDoc}
     */
    override fun getHashtagColors(): ColorStateList {
        return helper.getHashtagColors()
    }

    /**
     * {@inheritDoc}
     */
    override fun getMentionColors(): ColorStateList {
        return helper.getMentionColors()
    }

    /**
     * {@inheritDoc}
     */
    override fun getHyperlinkColors(): ColorStateList {
        return helper.getHyperlinkColors()
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagColors(colors: ColorStateList) {
        helper.setHashtagColors(colors)
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionColors(colors: ColorStateList) {
        helper.setMentionColors(colors)
    }

    /**
     * {@inheritDoc}
     */
    override fun setHyperlinkColors(colors: ColorStateList) {
        helper.setHyperlinkColors(colors)
    }

    /**
     * {@inheritDoc}
     */
    @ColorInt
    override fun getHashtagColor(): Int {
        return helper.getHashtagColor()
    }

    /**
     * {@inheritDoc}
     */
    @ColorInt
    override fun getMentionColor(): Int {
        return helper.getMentionColor()
    }

    /**
     * {@inheritDoc}
     */
    @ColorInt
    override fun getHyperlinkColor(): Int {
        return helper.getHyperlinkColor()
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagColor(@ColorInt color: Int) {
        helper.setHashtagColor(color)
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionColor(@ColorInt color: Int) {
        helper.setMentionColor(color)
    }

    /**
     * {@inheritDoc}
     */
    override fun setHyperlinkColor(@ColorInt color: Int) {
        helper.setHyperlinkColor(color)
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnHashtagClickListener(listener: SocialView.OnClickListener?) {
        helper.setOnHashtagClickListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnMentionClickListener(listener: SocialView.OnClickListener?) {
        helper.setOnMentionClickListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnHyperlinkClickListener(listener: SocialView.OnClickListener?) {
        helper.setOnHyperlinkClickListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagTextChangedListener(listener: SocialView.OnChangedListener?) {
        helper.setHashtagTextChangedListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionTextChangedListener(listener: SocialView.OnChangedListener?) {
        helper.setMentionTextChangedListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun getHashtags(): List<String?> {
        return helper.getHashtags()
    }

    /**
     * {@inheritDoc}
     */
    override fun getMentions(): List<String?> {
        return helper.getMentions()
    }

    /**
     * {@inheritDoc}
     */
    override fun getHyperlinks(): List<String?> {
        return helper.getHyperlinks()
    }

    init {
        helper = SocialViewHelper.install(this, attrs)
    }
}