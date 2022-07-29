package com.doubleclick.widdingkmm.android.Views.socialtextview.internal

import android.content.res.ColorStateList
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.util.PatternsCompat
import androidx.core.util.Supplier
import com.doubleclick.widdingkmm.android.Views.socialtextview.widget.SocialView
import com.doubleclick.widdingkmm.android.R
import java.util.regex.Pattern

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
class SocialViewHelper private constructor(view: TextView, attrs: AttributeSet?) :
    SocialView {
    private val view: TextView
    private val initialMovementMethod: MovementMethod
    private var hashtagPattern: Pattern? = null
    private var mentionPattern: Pattern? = null
    private var hyperlinkPattern: Pattern? = null
    private var flags: Int
    private var hashtagColors: ColorStateList
    private var mentionColors: ColorStateList
    private var hyperlinkColors: ColorStateList
    private var hashtagClickListener: SocialView.OnClickListener? = null
    private var mentionClickListener: SocialView.OnClickListener? = null
    private var hyperlinkClickListener: SocialView.OnClickListener? = null
    private var hashtagChangedListener: SocialView.OnChangedListener? = null
    private var mentionChangedListener: SocialView.OnChangedListener? = null
    private var hashtagEditing = false
    private var mentionEditing = false
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (count > 0 && start > 0) {
                val c = s[start - 1]
                when (c) {
                    '#' -> {
                        hashtagEditing = true
                        mentionEditing = false
                    }
                    '@' -> {
                        hashtagEditing = false
                        mentionEditing = true
                    }
                    else -> if (!Character.isLetterOrDigit(c)) {
                        hashtagEditing = false
                        mentionEditing = false
                    } else if (hashtagChangedListener != null && hashtagEditing) {
                        hashtagChangedListener!!.onChanged(
                            this@SocialViewHelper, s.subSequence(
                                indexOfPreviousNonLetterDigit(s, 0, start - 1) + 1, start
                            )
                        )
                    } else if (mentionChangedListener != null && mentionEditing) {
                        mentionChangedListener!!.onChanged(
                            this@SocialViewHelper, s.subSequence(
                                indexOfPreviousNonLetterDigit(s, 0, start - 1) + 1, start
                            )
                        )
                    }
                }
            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // triggered when text is added
            if (s.length == 0) {
                return
            }
            recolorize()
            if (start < s.length) {
                val index = start + count - 1
                if (index < 0) {
                    return
                }
                when (s[index]) {
                    '#' -> {
                        hashtagEditing = true
                        mentionEditing = false
                    }
                    '@' -> {
                        hashtagEditing = false
                        mentionEditing = true
                    }
                    else -> if (!Character.isLetterOrDigit(s[start])) {
                        hashtagEditing = false
                        mentionEditing = false
                    } else if (hashtagChangedListener != null && hashtagEditing) {
                        hashtagChangedListener!!.onChanged(
                            this@SocialViewHelper, s.subSequence(
                                indexOfPreviousNonLetterDigit(s, 0, start) + 1, start + count
                            )
                        )
                    } else if (mentionChangedListener != null && mentionEditing) {
                        mentionChangedListener!!.onChanged(
                            this@SocialViewHelper, s.subSequence(
                                indexOfPreviousNonLetterDigit(s, 0, start) + 1, start + count
                            )
                        )
                    }
                }
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun getHashtagPattern(): Pattern {
        return if (hashtagPattern != null) hashtagPattern!! else Pattern.compile("#(\\w+)")
    }

    override fun getMentionPattern(): Pattern {
        return if (mentionPattern != null) mentionPattern!! else Pattern.compile("@(\\w+)")
    }

    override fun getHyperlinkPattern(): Pattern {
        return if (hyperlinkPattern != null) hyperlinkPattern!! else PatternsCompat.WEB_URL
    }

    override fun setHashtagPattern(pattern: Pattern?) {
        if (hashtagPattern != pattern) {
            hashtagPattern = pattern
            recolorize()
        }
    }

    override fun setMentionPattern(pattern: Pattern?) {
        if (mentionPattern != pattern) {
            mentionPattern = pattern
            recolorize()
        }
    }

    override fun setHyperlinkPattern(pattern: Pattern?) {
        if (hyperlinkPattern != pattern) {
            hyperlinkPattern = pattern
            recolorize()
        }
    }

    override fun isHashtagEnabled(): Boolean {
        return flags or FLAG_HASHTAG == flags
    }

    override fun isMentionEnabled(): Boolean {
        return flags or FLAG_MENTION == flags
    }

    override fun isHyperlinkEnabled(): Boolean {
        return flags or FLAG_HYPERLINK == flags
    }

    override fun setHashtagEnabled(enabled: Boolean) {
        if (enabled != isHashtagEnabled()) {
            flags = if (enabled) flags or FLAG_HASHTAG else flags and FLAG_HASHTAG.inv()
            recolorize()
        }
    }

    override fun setMentionEnabled(enabled: Boolean) {
        if (enabled != isMentionEnabled()) {
            flags = if (enabled) flags or FLAG_MENTION else flags and FLAG_MENTION.inv()
            recolorize()
        }
    }

    override fun setHyperlinkEnabled(enabled: Boolean) {
        if (enabled != isHyperlinkEnabled()) {
            flags = if (enabled) flags or FLAG_HYPERLINK else flags and FLAG_HYPERLINK.inv()
            recolorize()
        }
    }

    override fun getHashtagColors(): ColorStateList {
        return hashtagColors
    }

    override fun getMentionColors(): ColorStateList {
        return mentionColors
    }

    override fun getHyperlinkColors(): ColorStateList {
        return hyperlinkColors
    }

    override fun setHashtagColors(colors: ColorStateList) {
        hashtagColors = colors
        recolorize()
    }

    override fun setMentionColors(colors: ColorStateList) {
        mentionColors = colors
        recolorize()
    }

    override fun setHyperlinkColors(colors: ColorStateList) {
        hyperlinkColors = colors
        recolorize()
    }

    override fun getHashtagColor(): Int {
        return getHashtagColors().defaultColor
    }

    override fun getMentionColor(): Int {
        return getMentionColors().defaultColor
    }

    override fun getHyperlinkColor(): Int {
        return getHyperlinkColors().defaultColor
    }

    override fun setHashtagColor(color: Int) {
        setHashtagColors(ColorStateList.valueOf(color))
    }

    override fun setMentionColor(color: Int) {
        setMentionColors(ColorStateList.valueOf(color))
    }

    override fun setHyperlinkColor(color: Int) {
        setHyperlinkColors(ColorStateList.valueOf(color))
    }

    override fun setOnHashtagClickListener(listener: SocialView.OnClickListener?) {
        ensureMovementMethod(listener)
        hashtagClickListener = listener
        recolorize()
    }

    override fun setOnMentionClickListener(listener: SocialView.OnClickListener?) {
        ensureMovementMethod(listener)
        mentionClickListener = listener
        recolorize()
    }

    override fun setOnHyperlinkClickListener(listener: SocialView.OnClickListener?) {
        ensureMovementMethod(listener)
        hyperlinkClickListener = listener
        recolorize()
    }

    override fun setHashtagTextChangedListener(listener: SocialView.OnChangedListener?) {
        hashtagChangedListener = listener
    }

    override fun setMentionTextChangedListener(listener: SocialView.OnChangedListener?) {
        mentionChangedListener = listener
    }

    override fun getHashtags(): List<String> {
        return listOf(view.text, getHashtagPattern(), false)
    }

    override fun getMentions(): List<String> {
        return listOf(view.text, getMentionPattern(), false)
    }

    override fun getHyperlinks(): List<String> {
        return listOf(view.text, getHyperlinkPattern(), true)
    }

    private fun ensureMovementMethod(listener: Any?) {
        if (listener == null) {
            view.movementMethod = initialMovementMethod
        } else if (view.movementMethod !is LinkMovementMethod) {
            view.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun recolorize() {
        val text = view.text
        check(text is Spannable) {
            "Attached text is not a Spannable," +
                    "add TextView.BufferType.SPANNABLE when setting text to this TextView."
        }
        val spannable = text
        for (span in spannable.getSpans(0, text.length, CharacterStyle::class.java)) {
            spannable.removeSpan(span)
        }
        if (isHashtagEnabled()) {
            spanAll(
                spannable, getHashtagPattern()
            ) {
                if (hashtagClickListener != null) SocialClickableSpan(
                    hashtagClickListener,
                    hashtagColors,
                    false
                ) else ForegroundColorSpan(hashtagColors.defaultColor)
            }
        }
        if (isMentionEnabled()) {
            spanAll(
                spannable, getMentionPattern()
            ) {
                if (mentionClickListener != null) SocialClickableSpan(
                    mentionClickListener,
                    mentionColors,
                    false
                ) else ForegroundColorSpan(mentionColors.defaultColor)
            }
        }
        if (isHyperlinkEnabled()) {
            spanAll(
                spannable, getHyperlinkPattern()
            ) {
                if (hyperlinkClickListener != null) SocialClickableSpan(
                    hyperlinkClickListener,
                    hyperlinkColors,
                    true
                ) else SocialURLSpan(text, hyperlinkColors)
            }
        }
    }

    /**
     * [CharacterStyle] that will be used for **hashtags**, **mentions**, and/or **hyperlinks**
     * when [OnClickListener] are activated.
     */
    inner class SocialClickableSpan(
        listener: SocialView.OnClickListener?,
        colors: ColorStateList,
        isHyperlink: Boolean
    ) :
        ClickableSpan() {
        private val listener: SocialView.OnClickListener?
        private val color: Int
        private val isHyperlink: Boolean
        var text: CharSequence? = null
        override fun onClick(widget: View) {
            check(widget is SocialView) { "Clicked widget is not an instance of SocialView." }
            (if (!isHyperlink) text!!.subSequence(1, text!!.length) else text)?.let {
                listener!!.onClick(
                    widget as SocialView,
                    it
                )
            }
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = color
            ds.isUnderlineText = isHyperlink
        }

        init {
            this.listener = listener
            color = colors.defaultColor
            this.isHyperlink = isHyperlink
        }
    }

    /**
     * Default [CharacterStyle] for **hyperlinks**.
     */
    private inner class SocialURLSpan(url: CharSequence, colors: ColorStateList) :
        URLSpan(url.toString()) {
        private val color: Int
        override fun updateDrawState(ds: TextPaint) {
            ds.color = color
            ds.isUnderlineText = true
        }

        init {
            color = colors.defaultColor
        }
    }

    companion object {
        private const val FLAG_HASHTAG = 1
        private const val FLAG_MENTION = 2
        private const val FLAG_HYPERLINK = 4

        /**
         * Configuring [SocialView] into given view.
         *
         * @param view TextView to install SocialView into.
         */
        fun install(view: TextView): SocialView {
            return SocialViewHelper(view, null)
        }

        /**
         * Configuring [SocialView] into given view.
         *
         * @param view  TextView to install SocialView into.
         * @param attrs The attributes from the View's constructor.
         */
        fun install(view: TextView, attrs: AttributeSet?): SocialView {
            return SocialViewHelper(view, attrs)
        }

        private fun indexOfNextNonLetterDigit(text: CharSequence, start: Int): Int {
            for (i in start + 1 until text.length) {
                if (!Character.isLetterOrDigit(text[i])) {
                    return i
                }
            }
            return text.length
        }

        private fun indexOfPreviousNonLetterDigit(text: CharSequence, start: Int, end: Int): Int {
            for (i in end downTo start + 1) {
                if (!Character.isLetterOrDigit(text[i])) {
                    return i
                }
            }
            return start
        }

        private fun spanAll(
            spannable: Spannable,
            pattern: Pattern,
            styleSupplier: Supplier<CharacterStyle>
        ) {
            val matcher = pattern.matcher(spannable)
            while (matcher.find()) {
                val start = matcher.start()
                val end = matcher.end()
                val span: Any = styleSupplier.get()
                spannable.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                if (span is SocialClickableSpan) {
                    span.text = spannable.subSequence(start, end)
                }
            }
        }

        private fun listOf(
            text: CharSequence,
            pattern: Pattern,
            isHyperlink: Boolean
        ): List<String> {
            val list: MutableList<String> = ArrayList()
            val matcher = pattern.matcher(text)
            while (matcher.find()) {
                list.add(
                    matcher.group(
                        if (!isHyperlink) 1 // remove hashtag and mention symbol
                        else 0
                    )
                )
            }
            return list
        }
    }

    init {
        this.view = view
        initialMovementMethod = view.movementMethod
        view.addTextChangedListener(textWatcher)
        view.setText(view.text, TextView.BufferType.SPANNABLE)
        val a = view.context.obtainStyledAttributes(
            attrs, R.styleable.SocialView, R.attr.socialViewStyle, R.style.Widget_SocialView
        )
        flags = a.getInteger(
            R.styleable.SocialView_socialFlags,
            FLAG_HASHTAG or FLAG_MENTION or FLAG_HYPERLINK
        )
        hashtagColors = a.getColorStateList(R.styleable.SocialView_HashtagColor)!!
        mentionColors = a.getColorStateList(R.styleable.SocialView_MentionColor)!!
        hyperlinkColors = a.getColorStateList(R.styleable.SocialView_HyperlinkColor)!!
        a.recycle()
        recolorize()
    }
}