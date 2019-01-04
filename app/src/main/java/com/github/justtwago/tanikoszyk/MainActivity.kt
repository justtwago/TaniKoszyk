package com.github.justtwago.tanikoszyk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepositoryImpl
import java.net.URL
import java.net.HttpURLConnection


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find()
    }

    private fun find() {
        val kauflandRepository = AuchanRepositoryImpl()
        val query = "cukier"
        kauflandRepository.getProducts(this, query)
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
