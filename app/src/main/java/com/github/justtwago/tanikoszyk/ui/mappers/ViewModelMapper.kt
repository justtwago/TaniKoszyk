package com.github.justtwago.tanikoszyk.ui.mappers

import com.github.justtwago.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.github.justtwago.usecases.model.market.common.Product

fun Product.toSearchProductViewModel(
    isInCart: Boolean, onClickListener: (Product) -> Boolean
) = SearchProductItemViewModel(
    id = id,
    subtitle = subtitle,
    title = title,
    price = price,
    imageUrl = imageUrl,
    quantity = quantity,
    market = market,
    onClickListener = onClickListener,
    isInCart = isInCart
)