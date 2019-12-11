package com.tanikoszyk.service.di

import com.fanmountain.authentication.Authenticator
import com.google.firebase.database.DatabaseReference
import com.tanikoszyk.service.repositories.*
import com.tanikoszyk.service.services.AuchanService
import com.tanikoszyk.service.services.BiedronkaService
import com.tanikoszyk.service.services.KauflandService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class RepositoryModule {

    @Provides
    fun cartRepository(
        databaseReference: DatabaseReference,
        authenticator: Authenticator
    ): CartRepository = FirebaseProductCartRepository(databaseReference, authenticator)

    @Provides
    fun auchanRepository(@Auchan retrofit: Retrofit): AuchanRepository = AuchanRepositoryImpl(
        retrofit.create(AuchanService::class.java)
    )

    @Provides
    fun biedronkaRepository(@Biedronka retrofit: Retrofit): BiedronkaRepository = BiedronkaRepositoryImpl(
        retrofit.create(BiedronkaService::class.java)
    )

    @Provides
    fun kauflandRepository(@Kaufland retrofit: Retrofit): KauflandRepository = KauflandRepositoryImpl(
        retrofit.create(KauflandService::class.java)
    )
}