package com.doubleclick.widdingkmm.android.Views.fabfilter.utils.ArcAnimator

import android.animation.ValueAnimator
import android.view.View

/**
 * Created By Eslam Ghazy on 12/20/2022
 */
class ArcAnimator private constructor(arcmetric: ArcMetric, target: View) :
    ValueAnimator() {
    var mArcMetric: ArcMetric
    private val mTarget: View

    //    private ValueAnimator mAnimator;
    private var mValue = 0f
    fun getDegree(): Float {
        return mValue
    }

    private fun setDegree(degree: Double) {
        mValue = degree.toFloat()
        val clipView = mTarget
        val x: Float = mArcMetric.getAxisPoint()!!.x + mArcMetric.mRadius * ArcUtils.cos(degree)
        val y: Float = mArcMetric.getAxisPoint()!!.y - mArcMetric.mRadius * ArcUtils.sin(degree)
        clipView.x = x - clipView.width / 2
        clipView.y = y - clipView.height / 2
    }

    override fun toString(): String {
        return mArcMetric.toString()
    }

    companion object {
        fun createArcAnimator(
            clipView: View, nestView: View?,
            degree: Float, side: Side?
        ): ArcAnimator {
            return createArcAnimator(
                clipView, ArcUtils.centerX(nestView!!),
                ArcUtils.centerY(nestView!!),
                degree, side
            )
        }

        fun createArcAnimator(
            clipView: View, endX: Float,
            endY: Float,
            degree: Float, side: Side?
        ): ArcAnimator {
            val arcMetric: ArcMetric = ArcMetric.evaluate(
                ArcUtils.centerX(clipView),
                ArcUtils.centerY(clipView),
                endX, endY, degree, side
            )
            return ArcAnimator(arcMetric, clipView)
        }

        fun createArcAnimator(
            clipView: View, startX: Float, startY: Float, endX: Float, endY: Float,
            degree: Float, side: Side?
        ): ValueAnimator {
            val arcMetric: ArcMetric = ArcMetric.evaluate(
                startX, startY, endX, endY,
                degree, side
            )
            return getAnimator(arcMetric, clipView)
        }

        private fun getAnimator(
            arcMetric: ArcMetric,
            target: View
        ): ValueAnimator {
            val valueAnimator = ofFloat(
                arcMetric.getStartDegree(),
                arcMetric.getEndDegree()
            )
            valueAnimator.addUpdateListener { animation: ValueAnimator ->
                val degree = animation.animatedValue as Double
                //                mValue = degree;
                val x: Float =
                    arcMetric.getAxisPoint()!!.x + arcMetric.mRadius * ArcUtils.cos(degree)
                val y: Float =
                    arcMetric.getAxisPoint()!!.y - arcMetric.mRadius * ArcUtils.sin(degree)
                target.x = x - target.width / 2
                target.y = y - target.height / 2
            }
            return valueAnimator
        }
    }

    init {
        mArcMetric = arcmetric
        mTarget = target
        ofFloat(arcmetric.getStartDegree(), arcmetric.getEndDegree())
        addUpdateListener { animation -> setDegree(animation.animatedValue as Double) }
    }
}
