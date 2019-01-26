package com.github.justtwago.tanikoszyk.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.justtwago.tanikoszyk.R
import com.github.justtwago.tanikoszyk.common.extensions.inflateChild
import com.github.justtwago.tanikoszyk.common.extensions.setTintColor
import kotlinx.android.synthetic.main.view_sort.view.*


class SortView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var isExpanded: Boolean = false
        private set

    init {
        inflateChild(R.layout.view_sort)
        setOnSortClicked()
        setOnTargetSortingSelected()
        setOnAlphabeticalAscendingSortingSelected()
        setOnAlphabeticalDescendingSortingSelected()
        setOnPriceAscendingSortingSelected()
        setOnPriceDescendingSortingSelected()
    }

    fun setOnTargetSortingSelected(action: (() -> Unit)? = null) {
        target.setOnClickListener {
            resetColorFocus()
            target.setColorFocus()
            action?.invoke()
        }
    }

    fun setOnAlphabeticalAscendingSortingSelected(action: (() -> Unit)? = null) {
        alphabeticalAsc.setOnClickListener {
            resetColorFocus()
            alphabeticalAsc.setColorFocus()
            action?.invoke()
        }
    }

    fun setOnAlphabeticalDescendingSortingSelected(action: (() -> Unit)? = null) {
        alphabeticalDesc.setOnClickListener {
            resetColorFocus()
            alphabeticalDesc.setColorFocus()
            action?.invoke()
        }
    }

    fun setOnPriceAscendingSortingSelected(action: (() -> Unit)? = null) {
        priceAsc.setOnClickListener {
            resetColorFocus()
            priceAsc.setColorFocus()
            action?.invoke()
        }
    }

    fun setOnPriceDescendingSortingSelected(action: (() -> Unit)? = null) {
        priceDesc.setOnClickListener {
            resetColorFocus()
            priceDesc.setColorFocus()
            action?.invoke()
        }
    }

    private fun setOnSortClicked() {
        sortButton.setOnClickListener {
            animateViews()
        }
    }

    private fun animateViews() {
        animateSortItems()
        toggleExpanding()
    }

    private fun animateSortItems() {
        val animation = if (isExpanded) {
            animateClosingItems()
        } else {
            animateOpeningItems()
        }
        animation.start()
    }

    private fun animateOpeningItems(): ViewPropertyAnimator {
        val scaleValue = 1f
        return sortIcon.animateRotation(180f)
            .withStartAction {
                target.animateScale(scaleValue, isDelayed = true)
                    .withStartAction {
                        alphabeticalAsc.animateScale(scaleValue, isDelayed = false)
                            .withStartAction {
                                alphabeticalDesc.animateScale(scaleValue, isDelayed = false)
                                    .withStartAction {
                                        priceAsc.animateScale(scaleValue, isDelayed = false)
                                            .withStartAction {
                                                priceDesc.animateScale(scaleValue, isDelayed = false)
                                            }
                                    }
                            }
                    }
            }
    }

    private fun animateClosingItems(): ViewPropertyAnimator {
        val scaleValue = 0f
        return sortIcon.animateRotation(0f)
            .withStartAction {
                priceDesc.animateScale(scaleValue, isDelayed = false)
                    .withStartAction {
                        priceAsc.animateScale(scaleValue, isDelayed = true)
                            .withStartAction {
                                alphabeticalDesc.animateScale(scaleValue, isDelayed = true)
                                    .withStartAction {
                                        alphabeticalAsc.animateScale(scaleValue, isDelayed = false)
                                            .withStartAction {
                                                target.animateScale(scaleValue, isDelayed = false)
                                            }
                                    }
                            }
                    }
            }

    }

    private fun View.animateRotation(angle: Float): ViewPropertyAnimator {
        return sortIcon
            .animate()
            .rotation(angle)
    }

    private fun View.animateScale(scaleValue: Float, isDelayed: Boolean): ViewPropertyAnimator {
        return animate()
            .scaleX(scaleValue)
            .scaleY(scaleValue)
            .apply {
                duration = 100
                if (isDelayed) {
                    startDelay = 50
                }
            }
    }

    private fun toggleExpanding() {
        isExpanded = !isExpanded
    }

    private fun resetColorFocus() {
        target.setTintColor(R.color.grey)
        alphabeticalAsc.setTintColor(R.color.grey)
        alphabeticalDesc.setTintColor(R.color.grey)
        priceAsc.setTintColor(R.color.grey)
        priceDesc.setTintColor(R.color.grey)
    }

    private fun ImageView.setColorFocus() {
        setTintColor(R.color.colorAccent)
    }
}