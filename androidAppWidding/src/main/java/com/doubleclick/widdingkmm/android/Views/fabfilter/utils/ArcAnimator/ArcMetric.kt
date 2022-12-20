package com.doubleclick.widdingkmm.android.Views.fabfilter.utils.ArcAnimator

import android.graphics.PointF
import java.util.*

/**
 * Created By Eslam Ghazy on 12/20/2022
 */
class ArcMetric {
    var mRadius = 0f
    var mStartPoint = PointF()
    var mEndPoint = PointF()
    var mMidPoint = PointF()
    var mAxisPoint = arrayOfNulls<PointF>(2)

    //SEGMENTS. This Segments create virtual triangle except mZeroStartSegment
    var mZeroPoint = PointF()
    var mStartEndSegment = 0f
    var mMidAxisSegment = 0f
    var mZeroStartSegment = 0f

    //DEGREES.
    var mAnimationDegree = 0f
    var mSideDegree = 0f
    var mZeroStartDegree = 0f
    var mStartDegree = 0f
    var mEndDegree = 0f

    //Side of animation
    var mSide: Side? = null
    private fun createAxisVariables() {
        for (i in mAxisPoint.indices) mAxisPoint[i] = PointF()
    }

    private fun calcStartEndSeg() {
        mStartEndSegment = Math.sqrt(
            Math.pow((mStartPoint.x - mEndPoint.x).toDouble(), 2.0) +
                    Math.pow((mStartPoint.y - mEndPoint.y).toDouble(), 2.0)
        ).toFloat()
    }

    private fun calcRadius() {
        mSideDegree = (180 - mAnimationDegree) / 2
        mRadius =
            mStartEndSegment / ArcUtils.sin(mAnimationDegree.toDouble()) * ArcUtils.sin(mSideDegree.toDouble())
    }

    private fun calcMidAxisSeg() {
        mMidAxisSegment = mRadius * ArcUtils.sin(mSideDegree.toDouble())
    }

    private fun calcMidPoint() {
        mMidPoint.x =
            mStartPoint.x + mStartEndSegment / 2 * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
        mMidPoint.y =
            mStartPoint.y + mStartEndSegment / 2 * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
    }

    private fun calcAxisPoints() {
        if (mStartPoint.y > mEndPoint.y || mStartPoint.y == mEndPoint.y) {
            mAxisPoint[0]!!.x =
                mMidPoint.x + mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
            mAxisPoint[0]!!.y =
                mMidPoint.y - mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
            mAxisPoint[1]!!.x =
                mMidPoint.x - mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
            mAxisPoint[1]!!.y =
                mMidPoint.y + mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
        } else {
            mAxisPoint[0]!!.x =
                mMidPoint.x - mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
            mAxisPoint[0]!!.y =
                mMidPoint.y + mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
            mAxisPoint[1]!!.x =
                mMidPoint.x + mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
            mAxisPoint[1]!!.y =
                mMidPoint.y - mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
        }
    }

    private fun calcZeroPoint() {
        when (mSide) {
            Side.RIGHT -> {
                mZeroPoint.x = mAxisPoint[Side.RIGHT.value]!!.x + mRadius
                mZeroPoint.y = mAxisPoint[Side.RIGHT.value]!!.y
            }
            Side.LEFT -> {
                mZeroPoint.x = mAxisPoint[Side.LEFT.value]!!.x - mRadius
                mZeroPoint.y = mAxisPoint[Side.LEFT.value]!!.y
            }
            else -> {}
        }
    }

    private fun calcDegrees() {
        mZeroStartSegment = Math.sqrt(
            Math.pow((mZeroPoint.x - mStartPoint.x).toDouble(), 2.0) +
                    Math.pow((mZeroPoint.y - mStartPoint.y).toDouble(), 2.0)
        ).toFloat()
        mZeroStartDegree = ArcUtils.acos(
            (2 * Math.pow(
                mRadius.toDouble(),
                2.0
            ) - Math.pow(mZeroStartSegment.toDouble(), 2.0)) / (2 * Math.pow(
                mRadius.toDouble(),
                2.0
            ))
        )
        when (mSide) {
            Side.RIGHT -> if (mStartPoint.y <= mZeroPoint.y) {
                if (mStartPoint.y > mEndPoint.y ||
                    mStartPoint.y == mEndPoint.y && mStartPoint.x > mEndPoint.x
                ) {
                    mStartDegree = mZeroStartDegree
                    mEndDegree = mStartDegree + mAnimationDegree
                } else {
                    mStartDegree = mZeroStartDegree
                    mEndDegree = mStartDegree - mAnimationDegree
                }
            } else if (mStartPoint.y >= mZeroPoint.y) {
                if (mStartPoint.y < mEndPoint.y ||
                    mStartPoint.y == mEndPoint.y && mStartPoint.x > mEndPoint.x
                ) {
                    mStartDegree = 0 - mZeroStartDegree
                    mEndDegree = mStartDegree - mAnimationDegree
                } else {
                    mStartDegree = 0 - mZeroStartDegree
                    mEndDegree = mStartDegree + mAnimationDegree
                }
            }
            Side.LEFT -> if (mStartPoint.y <= mZeroPoint.y) {
                if (mStartPoint.y > mEndPoint.y ||
                    mStartPoint.y == mEndPoint.y && mStartPoint.x < mEndPoint.x
                ) {
                    mStartDegree = 180 - mZeroStartDegree
                    mEndDegree = mStartDegree - mAnimationDegree
                } else {
                    mStartDegree = 180 - mZeroStartDegree
                    mEndDegree = mStartDegree + mAnimationDegree
                }
            } else if (mStartPoint.y >= mZeroPoint.y) {
                if (mStartPoint.y < mEndPoint.y ||
                    mStartPoint.y == mEndPoint.y && mStartPoint.x < mEndPoint.x
                ) {
                    mStartDegree = 180 + mZeroStartDegree
                    mEndDegree = mStartDegree + mAnimationDegree
                } else {
                    mStartDegree = 180 + mZeroStartDegree
                    mEndDegree = mStartDegree - mAnimationDegree
                }
            }
            else -> {}
        }
    }

    fun setDegree(degree: Float) {
        var degree = degree
        degree = Math.abs(degree)
        if (degree > 180) setDegree(degree % 180) else if (degree == 180f) setDegree(degree - 1) else if (degree < 30) setDegree(
            30f
        ) else mAnimationDegree = degree
    }

    fun getAxisPoint(): PointF? {
        return mAxisPoint[mSide!!.value]
    }

    /**
     * Return evaluated start degree
     *
     * @return the start degree
     */
    fun getStartDegree(): Float {
        return mStartDegree
    }

    /**
     * Return evaluated end degree
     *
     * @return the end degree
     */
    fun getEndDegree(): Float {
        return mEndDegree
    }

    fun getDegree(percentage: Float): Float {
        return mStartDegree + (mEndDegree - mStartDegree) * percentage
    }

    override fun toString(): String {
        return """ArcMetric{
mStartPoint=$mStartPoint
 mEndPoint=$mEndPoint
 mMidPoint=$mMidPoint
 mAxisPoint=${Arrays.toString(mAxisPoint)}
 mZeroPoint=$mZeroPoint
 mStartEndSegment=$mStartEndSegment
 mRadius=$mRadius
 mMidAxisSegment=$mMidAxisSegment
 mZeroStartSegment=$mZeroStartSegment
 mAnimationDegree=$mAnimationDegree
 mSideDegree=$mSideDegree
 mZeroStartDegree=$mZeroStartDegree
 mStartDegree=$mStartDegree
 mEndDegree=$mEndDegree
 mSide=$mSide}"""
    }

    companion object {
        /**
         * Create new [ArcMetric] instance and do all calculations below
         * and finally return ready to use object
         */
        fun evaluate(
            startX: Float, startY: Float,
            endX: Float, endY: Float,
            degree: Float, side: Side?
        ): ArcMetric {
            val arcMetric = ArcMetric()
            arcMetric.mStartPoint[startX] = startY
            arcMetric.mEndPoint[endX] = endY
            arcMetric.setDegree(degree)
            arcMetric.mSide = side
            arcMetric.createAxisVariables()
            arcMetric.calcStartEndSeg()
            arcMetric.calcRadius()
            arcMetric.calcMidAxisSeg()
            arcMetric.calcMidPoint()
            arcMetric.calcAxisPoints()
            arcMetric.calcZeroPoint()
            arcMetric.calcDegrees()
            return arcMetric
        }
    }
}
