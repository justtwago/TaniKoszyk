package com.github.justtwago.tanikoszyk.services.base

import com.github.justtwago.tanikoszyk.model.domain.Product
import com.github.justtwago.tanikoszyk.model.domain.ProductPage
import com.github.justtwago.tanikoszyk.model.domain.mapToDomain
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepository
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepository
import io.reactivex.Observable


interface BaseRepository {
    fun getProducts(searchQuery: String): Observable<ProductPage>
}

class BaseRepositoryImpl(
        private val auchanRepository: AuchanRepository,
        private val kauflandRepository: KauflandRepository,
        private val tescoRepository: TescoRepository
) : BaseRepository {


    override fun getProducts(searchQuery: String): Observable<ProductPage> {
        val auchanProductsSingle = auchanRepository.getProducts(searchQuery)
            .map { it.mapToDomain() }
            .toObservable()

        val kauflandProductsSingle = kauflandRepository.getProducts(searchQuery, page = 1)
            .map { it.mapToDomain() }
            .toObservable()

        val tescoProductsSingle = tescoRepository.getProducts(searchQuery, page = 1)
            .map { it.mapToDomain() }
            .toObservable()

        return auchanProductsSingle
            .concatWith(kauflandProductsSingle)
            .concatWith(tescoProductsSingle)
    }
}