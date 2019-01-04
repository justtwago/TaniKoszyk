package com.github.justtwago.tanikoszyk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.justtwago.tanikoszyk.services.auchan.AuchanRepository
import com.github.justtwago.tanikoszyk.services.base.BaseRepository
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val baseRepository: BaseRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find()
    }

    private fun find() {
        val query = "cukier"
        baseRepository.getProducts(query)
            .subscribe { page ->
                page.forEach {
                    Log.d(
                        "BLOGPOST",
                        "${it.subtitle}, ${it.title}, ${it.imageUrl}, ${it.oldPrice}, ${it.price}, ${it.quantity}, ${it.market.name}"
                    )
                }
                Log.d("BLOGPOST", "count: ${page.size}")
            }
    }
}
