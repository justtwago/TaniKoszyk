package com.tanikoszyk.service.model.data.biedronka

import pl.droidsonroids.jspoon.annotation.Selector

data class BiedronkaProductIdPage(
    @Selector(".result-item > a", attr = "href") var productIdList: List<String>? = null,
    @Selector(".pagination > ul > li") var pages: List<String>? = null
)