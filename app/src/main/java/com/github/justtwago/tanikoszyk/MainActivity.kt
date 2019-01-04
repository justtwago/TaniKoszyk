package com.github.justtwago.tanikoszyk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val auchanRepository: AuchanRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find()
    }

    private fun find() {
        val query = "cukier"
        auchanRepository.getProducts(query)
            .subscribe { page ->
                page.products.forEach {
                    Log.d(
                        "BLOGPOST",
                        "${it.subtitle}, ${it.title}, ${it.imageUrl}, ${it.priceZloty}, ${it.priceCents}, ${it.quanity}"
                    )
                }
                Log.d("BLOGPOST", "count: ${page.products.size}")
            }
    }
}
