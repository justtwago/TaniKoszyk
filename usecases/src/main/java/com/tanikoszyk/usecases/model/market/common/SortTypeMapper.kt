package com.tanikoszyk.usecases.model.market.common

import com.tanikoszyk.service.model.service.SortTypeService


fun SortType.mapToService(): SortTypeService {
    return when (this) {
        SortType.ALPHABETICAL_ASCEND -> SortTypeService.ALPHABETICAL_ASCEND
        SortType.ALPHABETICAL_DESCEND -> SortTypeService.ALPHABETICAL_DESCEND
        SortType.PRICE_ASCEND -> SortTypeService.PRICE_ASCEND
        SortType.PRICE_DESCEND -> SortTypeService.PRICE_DESCEND
        SortType.TARGET -> SortTypeService.TARGET
    }
}

fun SortTypeService.mapToDomain(): SortType {
    return when (this) {
        SortTypeService.ALPHABETICAL_ASCEND -> SortType.ALPHABETICAL_ASCEND
        SortTypeService.ALPHABETICAL_DESCEND -> SortType.ALPHABETICAL_DESCEND
        SortTypeService.PRICE_ASCEND -> SortType.PRICE_ASCEND
        SortTypeService.PRICE_DESCEND -> SortType.PRICE_DESCEND
        SortTypeService.TARGET -> SortType.TARGET
    }
}