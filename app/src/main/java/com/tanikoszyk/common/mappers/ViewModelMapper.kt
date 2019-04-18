package com.tanikoszyk.common.mappers

import com.tanikoszyk.ui.home.list.SearchProductItemViewModel
import com.tanikoszyk.usecases.model.market.common.Product

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