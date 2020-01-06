package com.tanikoszyk.di.application

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tanikoszyk.di.viewmodel.ViewModelModule
import com.tanikoszyk.service.di.ServiceComponent
import com.tanikoszyk.storage.di.StorageModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [ServiceComponent::class],
    modules = [ViewModelModule::class, StorageModule::class]
)
interface AppComponent {

    val viewModelFactory: ViewModelProvider.Factory

    fun inject(applicationContext: Context)

    @Component.Factory
    interface Factory {

        fun create(
            serviceComponent: ServiceComponent,
            @BindsInstance applicationContext: Context
        ): AppComponent
    }
}

interface AppComponentProvider {
    val appComponent: AppComponent
}

val Context.appComponent: AppComponent
    get() = (applicationContext as AppComponentProvider).appComponent

val Fragment.appComponent: AppComponent
    get() = requireContext().appComponent