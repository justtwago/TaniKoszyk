package com.github.justtwago.tanikoszyk.services.base

import com.github.justtwago.tanikoszyk.model.domain.Product
import com.github.justtwago.tanikoszyk.model.domain.mapToDomain
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import com.github.justtwago.tanikoszyk.services.kaufland.KauflandRepository
import com.github.justtwago.tanikoszyk.services.tesco.TescoRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


interface BaseRepository {
    fun getProducts(searchQuery: String): Observable<List<Product>>
}

class BaseRepositoryImpl(
    private val auchanRepository: AuchanRepository,
    private val kauflandRepository: KauflandRepository,
    private val tescoRepository: TescoRepository
) : BaseRepository {
    override fun getProducts(searchQuery: String): Observable<List<Product>> {
        val auchanProductsSingle = auchanRepository.getProducts(searchQuery)
            .map { it.mapToDomain() }
            .toObservable()

        val kauflandProductsSingle = kauflandRepository.getProducts(searchQuery)
            .map { it.mapToDomain() }
            .toObservable()

        val tescoProductsSingle = tescoRepository.getProducts(searchQuery)
            .map { it.mapToDomain() }
            .toObservable()

        return auchanProductsSingle
            .concatWith(kauflandProductsSingle)
            .concatWith(tescoProductsSingle)
    }
}