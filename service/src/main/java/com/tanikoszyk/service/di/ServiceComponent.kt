package com.tanikoszyk.service.di

import android.content.Context
import com.fanmountain.authentication.AuthenticatorComponent
import com.tanikoszyk.service.repositories.AuchanRepository
import com.tanikoszyk.service.repositories.BiedronkaRepository
import com.tanikoszyk.service.repositories.CartRepository
import com.tanikoszyk.service.repositories.KauflandRepository
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AuthenticatorComponent::class],
    modules = [ServiceModule::class, RepositoryModule::class]
)
interface ServiceComponent {

    val cartRepository: CartRepository
    val auchanRepository: AuchanRepository
    val biedronkaRepository: BiedronkaRepository
    val kauflandRepository: KauflandRepository

    fun inject(context: Context)

    @Component.Factory
    interface Factory {

        fun create(
            authenticatorComponent: AuthenticatorComponent,
            @BindsInstance context: Context
        ): ServiceComponent
    }
}