package com.doubleclick.widdingkmm.android.Views.socialtextview

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import com.doubleclick.widdingkmm.android.R
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created By Eslam Ghazy on 7/18/2022
 */
class SocialTextView @JvmOverloads constructor(
    c: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) :
    AppCompatTextView(c, attrs, def) {
    /* Mutable properties */
    private val underlineEnabled: Boolean
    private val selectedColor: Int
    private val hashtagColor: Int
    private val mentionColor: Int
    private val phoneColor: Int
    private val emailColor: Int
    private val urlColor: Int
    private var linkClickListener: OnLinkClickListener? = null

    /* Stores enabled link modes */
    private val flags: Int

    /**
     * Overridden to ensure that highlighted text is always transparent.
     */
    override fun setHighlightColor(@ColorInt color: Int) {
        super.setHighlightColor(Color.TRANSPARENT)
    }

    fun setLinkText(text: String?) {
        setText(createSocialMediaSpan(text))
    }

    fun appendLinkText(text: String?) {
        append(createSocialMediaSpan(text))
    }

    fun setLinkHint(textHint: String?) {
        hint = createSocialMediaSpan(textHint)
    }

    fun setOnLinkClickListener(linkClickListener: OnLinkClickListener?) {
        this.linkClickListener = linkClickListener
    }

    fun getOnLinkClickListener(): OnLinkClickListener? {
        return linkClickListener
    }

    /**
     * Creates a spannable string containing the touchable spans of each link item
     * for each enabled link mode in the given text.
     *
     * @param text Text
     * @return [SpannableString]
     */
    private fun createSocialMediaSpan(text: String?): SpannableString {
        val items = collectLinkItemsFromText(text)
        val textSpan = SpannableString(text)

        // Create a span for each link item
        for (item in items) {
            textSpan.setSpan(object :
                TouchableSpan(getColorByMode(item.mode), selectedColor, underlineEnabled) {
                override fun onClick(widget: View) {
                    // Trigger callback when span is touched
                    if (linkClickListener != null) {
                        linkClickListener!!.onLinkClicked(item.mode, item.matched)
                    }
                }

            }, item.start, item.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return textSpan
    }

    /**
     * Checks which flags are enable so that the appropriate link items can be
     * collected from each respective mode.
     *
     * @param text Text
     * @return Set of [LinkItem]
     */
    private fun collectLinkItemsFromText(text: String?): Set<LinkItem> {
        val items: MutableSet<LinkItem> = HashSet()

        // Check for hashtag links, if possible
        if (flags and HASHTAG == HASHTAG) {
            collectLinkItems(HASHTAG, items, hashtagPattern!!.matcher(text))
        }

        // Check for mention links, if possible
        if (flags and MENTION == MENTION) {
            collectLinkItems(MENTION, items, mentionPattern!!.matcher(text))
        }

        // Check for phone links, if possible
        if (flags and PHONE == PHONE) {
            collectLinkItems(PHONE, items, Patterns.PHONE.matcher(text))
        }

        // Check for url links, if possible
        if (flags and URL == URL) {
            collectLinkItems(URL, items, Patterns.WEB_URL.matcher(text))
        }

        // Check for email links, if possible
        if (flags and EMAIL == EMAIL) {
            collectLinkItems(EMAIL, items, Patterns.EMAIL_ADDRESS.matcher(text))
        }
        return items
    }

    /**
     * Iterates through all the matches found by the given matcher and adds a new
     * [LinkItem] for each match into the given collection of link items.
     *
     * @param mode    [LinkOptions]
     * @param items   Collection of [LinkItem]
     * @param matcher [Matcher]
     */
    private fun collectLinkItems(
        @LinkOptions mode: Int,
        items: MutableCollection<LinkItem>,
        matcher: Matcher
    ) {
        while (matcher.find()) {
            var matcherStart = matcher.start()
            var matchedText = matcher.group()
            if (matchedText.startsWith(" ")) {
                matcherStart += 1
                matchedText = matchedText.substring(1)
            }
            items.add(
                LinkItem(
                    matchedText,
                    matcherStart,
                    matcher.end(),
                    mode
                )
            )
        }
    }

    /**
     * Gets the corresponding color for a given mode.
     *
     * @param mode [.HASHTAG], [.MENTION], [.EMAIL], [.PHONE], [.URL]
     * @return Color
     */
    private fun getColorByMode(@LinkOptions mode: Int): Int {
        return when (mode) {
            HASHTAG -> hashtagColor
            MENTION -> mentionColor
            PHONE -> phoneColor
            URL -> urlColor
            EMAIL -> emailColor
            else -> throw IllegalArgumentException("Invalid mode!")
        }
    }

    /**
     * Data structure to store information about a link.
     */
    private inner class LinkItem(
        val matched: String,
        val start: Int,
        val end: Int,
        val mode: Int
    )

    @Retention(RetentionPolicy.SOURCE)
    @Target(
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.LOCAL_VARIABLE,
        AnnotationTarget.FIELD
    )
    @IntDef(value = [HASHTAG, MENTION, PHONE, EMAIL, URL])
    annotation class LinkOptions

    /**
     * Listener for link clicks in text view.
     */
    interface OnLinkClickListener {
        fun onLinkClicked(linkType: Int, matchedText: String?)
    }

    companion object {
        /* Constants for social media flags */
        private const val HASHTAG = 1
        private const val MENTION = 2
        private const val PHONE = 4
        private const val URL = 8
        private const val EMAIL = 16
        private var patternHashtag: Pattern? = null
        private var patternMention: Pattern? = null

        /**
         * Lazy loads the 'hashtag' regex pattern.
         *
         * @return [Pattern]
         */
        private val hashtagPattern: Pattern?
            private get() {
                if (patternHashtag == null) {
                    patternHashtag = Pattern.compile("(?:^|\\s|$)#[\\p{L}0-9_]*")
                }
                return patternHashtag
            }

        /**
         * Lazy loads the 'mention' regex pattern.
         *
         * @return [Pattern]
         */
        private val mentionPattern: Pattern?
            private get() {
                if (patternMention == null) {
                    patternMention = Pattern.compile("(?:^|\\s|$|[.])@[\\p{L}0-9_]*")
                }
                return patternMention
            }
    }

    init {

        // Set the link movement method by default
        movementMethod = AccurateMovementMethod.instance

        // Set XML attributes
        val a = c.obtainStyledAttributes(attrs, R.styleable.SocialTextView)
        flags = a.getInt(R.styleable.SocialTextView_linkModes, -1)
        hashtagColor = a.getColor(R.styleable.SocialTextView_hashtagColor, Color.RED)
        mentionColor = a.getColor(R.styleable.SocialTextView_mentionColor, Color.RED)
        phoneColor = a.getColor(R.styleable.SocialTextView_phoneColor, Color.RED)
        emailColor = a.getColor(R.styleable.SocialTextView_emailColor, Color.RED)
        urlColor = a.getColor(R.styleable.SocialTextView_urlColor, Color.RED)
        selectedColor = a.getColor(R.styleable.SocialTextView_selectedColor, Color.LTGRAY)
        underlineEnabled = a.getBoolean(R.styleable.SocialTextView_underlineEnabled, false)
        if (a.hasValue(R.styleable.SocialTextView_android_text)) {
            setLinkText(a.getString(R.styleable.SocialTextView_android_text))
        }
        if (a.hasValue(R.styleable.SocialTextView_android_hint)) {
            setLinkHint(a.getString(R.styleable.SocialTextView_android_hint))
        }
        a.recycle()
    }
}