package com.healthtracker.app.ui.components

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.card.MaterialCardView

/**
 * Custom CardView that implements the "lift" animation per UI spec.
 * Card scales up slightly and increases elevation on touch,
 * providing immediate feedback to the user.
 */
class LiftCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    companion object {
        private const val LIFT_SCALE = 1.03f
        private const val LIFT_ELEVATION_DP = 12f
        private const val ANIMATION_DURATION = 100L
    }

    private val defaultElevation = elevation
    private val liftElevation = LIFT_ELEVATION_DP * resources.displayMetrics.density
    
    private var isLifted = false

    init {
        // Ensure card has default styling
        radius = 16f * resources.displayMetrics.density
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                animateLift()
                return true
            }
            MotionEvent.ACTION_UP -> {
                animateDrop()
                performClick()
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                animateDrop()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    /**
     * Animate the card lifting up (scale and elevation increase)
     */
    private fun animateLift() {
        if (isLifted) return
        isLifted = true

        val scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1f, LIFT_SCALE)
        val scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1f, LIFT_SCALE)
        val elevationAnimator = ObjectAnimator.ofFloat(this, "elevation", defaultElevation, liftElevation)

        AnimatorSet().apply {
            playTogether(scaleXAnimator, scaleYAnimator, elevationAnimator)
            duration = ANIMATION_DURATION
            start()
        }
    }

    /**
     * Animate the card dropping back (scale and elevation decrease)
     */
    private fun animateDrop() {
        if (!isLifted) return
        isLifted = false

        val scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", LIFT_SCALE, 1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", LIFT_SCALE, 1f)
        val elevationAnimator = ObjectAnimator.ofFloat(this, "elevation", liftElevation, defaultElevation)

        AnimatorSet().apply {
            playTogether(scaleXAnimator, scaleYAnimator, elevationAnimator)
            duration = ANIMATION_DURATION
            start()
        }
    }
}
