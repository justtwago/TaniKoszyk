package com.tanikoszyk.service.di

import android.content.Context
import com.tanikoszyk.service.repositories.AuchanRepository
import com.tanikoszyk.service.repositories.BiedronkaRepository
import com.tanikoszyk.service.repositories.KauflandRepository
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ServiceModule::class, RepositoryModule::class]
)
interface ServiceComponent {

    val auchanRepository: AuchanRepository
    val biedronkaRepository: BiedronkaRepository
    val kauflandRepository: KauflandRepository

    fun inject(context: Context)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ServiceComponent
    }
}