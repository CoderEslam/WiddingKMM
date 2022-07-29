package com.doubleclick.widdingkmm.android.Views.SwipeToReply

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.R

/**
 * Created By Eslam Ghazy on 7/15/2022
 */
class SwipeController : ItemTouchHelper.Callback {
    private var mContext: Context
    private var mSwipeControllerActions: ISwipeControllerActions
    private var mReplyIcon: Drawable
    private var mReplyIconBackground: Drawable
    private var mCurrentViewHolder: RecyclerView.ViewHolder? = null
    private var mView: View? = null
    private var mDx = 0f
    private var mReplyButtonProgress = 0f
    private var mLastReplyButtonAnimationTime: Long = 0
    private var mSwipeBack = false
    private var mIsVibrating = false
    private var mStartTracking = false
    private var mBackgroundColor = 0x20606060
    private val mReplyBackgroundOffset = 18
    private val mReplyIconXOffset = 12
    private val mReplyIconYOffset = 11

    @SuppressLint("UseCompatLoadingForDrawables")
    constructor(context: Context, swipeControllerActions: ISwipeControllerActions) {
        mContext = context
        mSwipeControllerActions = swipeControllerActions
        mReplyIcon = mContext.resources.getDrawable(R.drawable.ic_reply_black_24dp)
        mReplyIconBackground = mContext.resources.getDrawable(R.drawable.ic_round_shape)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    constructor(
        context: Context,
        swipeControllerActions: ISwipeControllerActions,
        replyIcon: Int,
        replyIconBackground: Int,
        backgroundColor: Int
    ) {
        mContext = context
        mSwipeControllerActions = swipeControllerActions
        mReplyIcon = mContext.resources.getDrawable(replyIcon)
        mReplyIconBackground = mContext.resources.getDrawable(replyIconBackground)
        mBackgroundColor = backgroundColor
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        mView = viewHolder.itemView
        return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        viewHolder1: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {}
    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (mSwipeBack) {
            mSwipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            setTouchListener(recyclerView, viewHolder)
        }
        if (mView!!.translationX < convertToDp(130) || dX < mDx) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            mDx = dX
            mStartTracking = true
        }
        mCurrentViewHolder = viewHolder
        drawReplyButton(c)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        recyclerView.setOnTouchListener { v, event ->
            mSwipeBack =
                if (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP) {
                    true
                } else {
                    false
                }
            if (mSwipeBack) {
                if (Math.abs(mView!!.translationX) >= convertToDp(100)) {
                    mSwipeControllerActions.onSwipePerformed(viewHolder.adapterPosition)
                }
            }
            false
        }
    }

    private fun convertToDp(pixels: Int): Int {
        return getDP(pixels.toFloat(), mContext)
    }

    private fun drawReplyButton(canvas: Canvas) {
        if (mCurrentViewHolder == null) {
            return
        }
        val translationX = mView!!.translationX
        val newTime = System.currentTimeMillis()
        val dt = Math.min(17, newTime - mLastReplyButtonAnimationTime)
        mLastReplyButtonAnimationTime = newTime
        var showing = false
        if (translationX >= convertToDp(30)) {
            showing = true
        }
        if (showing) {
            if (mReplyButtonProgress < 1.0f) {
                mReplyButtonProgress += dt / 180.0f
                if (mReplyButtonProgress > 1.0f) {
                    mReplyButtonProgress = 1.0f
                } else {
                    mView!!.invalidate()
                }
            }
        } else if (translationX <= 0.0f) {
            mReplyButtonProgress = 0f
            mStartTracking = false
            mIsVibrating = false
        } else {
            if (mReplyButtonProgress > 0.0f) {
                mReplyButtonProgress -= dt / 180.0f
                if (mReplyButtonProgress < 0.1f) {
                    mReplyButtonProgress = 0f
                }
            }
            mView!!.invalidate()
        }
        val alpha: Int
        val scale: Float
        if (showing) {
            scale = if (mReplyButtonProgress <= 0.8f) {
                1.2f * (mReplyButtonProgress / 0.8f)
            } else {
                1.2f - 0.2f * ((mReplyButtonProgress - 0.8f) / 0.2f)
            }
            alpha = Math.min(255, 255 * (mReplyButtonProgress / 0.8f).toInt())
        } else {
            scale = mReplyButtonProgress
            alpha = Math.min(255, 255 * mReplyButtonProgress.toInt())
        }
        mReplyIconBackground.alpha = alpha
        mReplyIcon.alpha = alpha
        if (mStartTracking) {
            if (!mIsVibrating && mView!!.translationX >= convertToDp(100)) {
                mView!!.performHapticFeedback(
                    HapticFeedbackConstants.KEYBOARD_TAP,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                )
            }
            mIsVibrating = true
        }
        val x: Int
        val y: Float
        x = if (mView!!.translationX > convertToDp(130)) {
            convertToDp(130) / 2
        } else {
            mView!!.translationX.toInt() / 2
        }
        y = mView!!.top + mView!!.measuredHeight.toFloat() / 2
        mReplyIconBackground.setColorFilter(mBackgroundColor, PorterDuff.Mode.MULTIPLY)
        mReplyIconBackground.bounds = Rect(
            (x - convertToDp(mReplyBackgroundOffset) * scale).toInt(),
            (y - convertToDp(mReplyBackgroundOffset) * scale).toInt(),
            (x + convertToDp(mReplyBackgroundOffset) * scale).toInt(),
            (y + convertToDp(mReplyBackgroundOffset) * scale).toInt()
        )
        mReplyIconBackground.draw(canvas)
        mReplyIcon.bounds = Rect(
            (x - convertToDp(mReplyIconXOffset) * scale).toInt(),
            (y - convertToDp(mReplyIconYOffset) * scale).toInt(),
            (x + convertToDp(mReplyIconXOffset) * scale).toInt(),
            (y + convertToDp(mReplyIconYOffset) * scale).toInt()
        )
        mReplyIcon.draw(canvas)
        mReplyIconBackground.alpha = 255
        mReplyIcon.alpha = 255
    }

    private fun getDP(toDP: Float, context: Context): Int {
        return if (toDP == 0f) {
            0
        } else {
            val density = context.resources.displayMetrics.density
            Math.ceil((density * toDP).toDouble()).toInt()
        }
    }
}

