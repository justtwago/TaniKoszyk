package com.github.justtwago.service.di

import com.github.justtwago.service.firebase.FirebaseAuthenticator
import com.github.justtwago.service.firebase.FirebaseAuthenticatorImpl
import com.github.justtwago.service.firebase.FirebaseProductCartRepository
import com.github.justtwago.service.firebase.FirebaseProductCartRepositoryImpl
import com.github.justtwago.service.repositories.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module


val serviceModule = module {
    single { FirebaseDatabase.getInstance().reference }
    single { FirebaseAuth.getInstance() }
    single { FirebaseProductCartRepositoryImpl(databaseReference = get()) as FirebaseProductCartRepository }
    single { FirebaseAuthenticatorImpl(firebaseAuth = get()) as FirebaseAuthenticator }
    single { AuchanRepositoryImpl(androidApplication()) as AuchanRepository }
    single { KauflandRepositoryImpl(androidApplication()) as KauflandRepository }
    single { TescoRepositoryImpl(androidApplication()) as TescoRepository }
    single { BiedronkaRepositoryImpl(androidApplication()) as BiedronkaRepository }
}