package com.github.justtwago.tanikoszyk.ui.mappers

import com.github.justtwago.tanikoszyk.ui.home.list.ProductItemViewModel
import com.github.justtwago.usecases.model.market.common.Product


fun Product.toViewModel() = ProductItemViewModel(
    id = id,
    subtitle = subtitle,
    title = title,
    oldPrice = oldPrice,
    price = price,
    imageUrl = imageUrl,
    quantity = quantity,
    market = market
)