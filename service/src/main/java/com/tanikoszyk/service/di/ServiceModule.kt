package com.tanikoszyk.service.di

import android.app.Application
import com.fanmountain.authentication.Authenticator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tanikoszyk.service.common.createRetrofit
import com.tanikoszyk.service.repositories.*
import com.tanikoszyk.service.services.AuchanService
import com.tanikoszyk.service.services.BiedronkaService
import com.tanikoszyk.service.services.KauflandService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun firebaseDatabaseReference() = FirebaseDatabase.getInstance().reference

    @Provides
    @Singleton
    fun firebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    @Auchan
    fun auchanRetrofit(application: Application) = createRetrofit(application, AUCHAN_BASE_URL)

    @Provides
    @Singleton
    @Biedronka
    fun biedronkaRetrofit(application: Application) = createRetrofit(application, BIEDRONKA_BASE_URL)

    @Provides
    @Singleton
    @Kaufland
    fun kauflandRetrofit(application: Application) = createRetrofit(application, KAUFLAND_BASE_URL)

    @Provides
    @Singleton
    fun firebaseProductCartRepository(
        databaseReference: DatabaseReference,
        authenticator: Authenticator
    ): CartRepository = FirebaseProductCartRepository(databaseReference, authenticator)

    @Provides
    @Singleton
    fun auchanRepository(@Auchan retrofit: Retrofit): AuchanRepository = AuchanRepositoryImpl(
        retrofit.create(AuchanService::class.java)
    )

    @Provides
    @Singleton
    fun biedronkaRepository(@Biedronka retrofit: Retrofit): BiedronkaRepository = BiedronkaRepositoryImpl(
        retrofit.create(BiedronkaService::class.java)
    )

    @Provides
    @Singleton
    fun kauflandRepository(@Kaufland retrofit: Retrofit): KauflandRepository = KauflandRepositoryImpl(
        retrofit.create(KauflandService::class.java)
    )
}

@Qualifier annotation class Auchan
@Qualifier annotation class Biedronka
@Qualifier annotation class Kaufland