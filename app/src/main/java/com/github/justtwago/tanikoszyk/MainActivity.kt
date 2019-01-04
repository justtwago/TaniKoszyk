package com.github.justtwago.tanikoszyk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.justtwago.tanikoszyk.services.KauflandRepositoryImpl
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val kauflandRepository = KauflandRepositoryImpl()
        kauflandRepository.getProducts("kurczak")
            .subscribe { page ->
                page.products.forEach {
                    Log.d("BLOGPOST", it.subtitle)
                    Log.d("BLOGPOST", it.title)
                    Log.d("BLOGPOST", it.imageUrl.first())
                    Log.d("BLOGPOST", it.price)
                    Log.d("BLOGPOST", it.quanity)
                }
            }
    }
}
