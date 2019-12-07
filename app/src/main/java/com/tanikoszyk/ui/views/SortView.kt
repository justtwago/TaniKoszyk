package com.tanikoszyk.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fanmountain.domain.SortType
import com.tanikoszyk.R
import com.tanikoszyk.common.extensions.inflateChild
import com.tanikoszyk.common.extensions.setTintColor
import kotlinx.android.synthetic.main.view_sort.view.*


class SortView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var isExpanded: Boolean = false
        private set

    init {
        inflateChild(R.layout.view_sort)
        setOnSortClicked()
        setOnSortItemSelectedListener()
    }

    fun setOnSortItemSelectedListener(listener: ((SortType) -> Unit)? = null) {
        setOnTargetSortingSelected(listener)
        setOnAlphabeticalAscendingSortingSelected(listener)
        setOnAlphabeticalDescendingSortingSelected(listener)
        setOnPriceAscendingSortingSelected(listener)
        setOnPriceDescendingSortingSelected(listener)
    }

    private fun setOnTargetSortingSelected(listener: ((SortType) -> Unit)?) {
        target.setOnClickListener {
            resetColorFocus()
            target.setColorFocus()
            listener?.invoke(SortType.TARGET)
        }
    }

    private fun setOnAlphabeticalAscendingSortingSelected(listener: ((SortType) -> Unit)?) {
        alphabeticalAsc.setOnClickListener {
            resetColorFocus()
            alphabeticalAsc.setColorFocus()
            listener?.invoke(SortType.ALPHABETICAL_ASCEND)
        }
    }

    private fun setOnAlphabeticalDescendingSortingSelected(listener: ((SortType) -> Unit)?) {
        alphabeticalDesc.setOnClickListener {
            resetColorFocus()
            alphabeticalDesc.setColorFocus()
            listener?.invoke(SortType.ALPHABETICAL_DESCEND)
        }
    }

    private fun setOnPriceAscendingSortingSelected(listener: ((SortType) -> Unit)?) {
        priceAsc.setOnClickListener {
            resetColorFocus()
            priceAsc.setColorFocus()
            listener?.invoke(SortType.PRICE_ASCEND)
        }
    }

    private fun setOnPriceDescendingSortingSelected(listener: ((SortType) -> Unit)?) {
        priceDesc.setOnClickListener {
            resetColorFocus()
            priceDesc.setColorFocus()
            listener?.invoke(SortType.ALPHABETICAL_DESCEND)
        }
    }

    private fun setOnSortClicked() {
        sortButton.setOnClickListener {
            animateViews()
        }
    }

    private fun animateViews() {
        toggleExpanding()
        animateSortItems()
    }

    private fun animateSortItems() {
        val animation = if (isExpanded) {
            animateOpeningItems()
        } else {
            animateClosingItems()
        }
        animation.start()
    }

    private fun animateOpeningItems(): ViewPropertyAnimator {
        return sortIcon.animateRotation(180f)
            .withStartAction {
                target.animateScale(isDelayed = true)
                    .withStartAction {
                        alphabeticalAsc.animateScale(isDelayed = false)
                            .withStartAction {
                                alphabeticalDesc.animateScale(isDelayed = false)
                                    .withStartAction {
                                        priceAsc.animateScale(isDelayed = false)
                                            .withStartAction {
                                                priceDesc.animateScale(isDelayed = false)
                                            }
                                    }
                            }
                    }
            }
    }

    private fun animateClosingItems(): ViewPropertyAnimator {
        return sortIcon.animateRotation(0f)
            .withStartAction {
                priceDesc.animateScale(isDelayed = false)
                    .withStartAction {
                        priceAsc.animateScale(isDelayed = true)
                            .withStartAction {
                                alphabeticalDesc.animateScale(isDelayed = true)
                                    .withStartAction {
                                        alphabeticalAsc.animateScale(isDelayed = false)
                                            .withStartAction {
                                                target.animateScale(isDelayed = false)
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

    private fun View.animateScale(isDelayed: Boolean): ViewPropertyAnimator {
        val scaleValue = if (isExpanded) 1f else 0f
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