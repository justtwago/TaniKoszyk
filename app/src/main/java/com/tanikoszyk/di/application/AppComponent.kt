package com.tanikoszyk.di.application

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fanmountain.authentication.AuthenticationModule
import com.tanikoszyk.TaniKoszykApplication
import com.tanikoszyk.di.viewmodel.ViewModelModule
import com.tanikoszyk.service.di.ServiceModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AuthenticationModule::class,
        ViewModelModule::class,
        ServiceModule::class
    ]
)
interface AppComponent {

    val viewModelFactory: ViewModelProvider.Factory

    fun inject(application: TaniKoszykApplication)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }
}

interface AppComponentProvider {
    val appComponent: AppComponent
}

val Context.appComponent: AppComponent
    get() = (applicationContext as AppComponentProvider).appComponent

val Fragment.appComponent: AppComponent
    get() = requireContext().appComponent