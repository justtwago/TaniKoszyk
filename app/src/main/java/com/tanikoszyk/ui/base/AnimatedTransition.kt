package com.tanikoszyk.ui.base

import android.view.animation.AccelerateDecelerateInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionSet

class AnimatedTransition(orderType: Int = ORDERING_TOGETHER) : TransitionSet() {

    init {
        ordering = orderType
        addTransition(Fade(Fade.OUT))
            .addTransition(ChangeBounds())
            .addTransition(Fade(Fade.IN))
            .apply {
                duration = 200
                interpolator = AccelerateDecelerateInterpolator()
            }
    }

}