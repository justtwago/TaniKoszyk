package com.tanikoszyk.service.di

import com.tanikoszyk.service.firebase.FirebaseAuthenticator
import com.tanikoszyk.service.firebase.FirebaseAuthenticatorImpl
import com.tanikoszyk.service.firebase.FirebaseProductCartRepository
import com.tanikoszyk.service.firebase.FirebaseProductCartRepositoryImpl
import com.tanikoszyk.service.repositories.*
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