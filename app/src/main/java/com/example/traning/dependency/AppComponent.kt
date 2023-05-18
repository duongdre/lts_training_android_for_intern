package com.example.traning.dependency

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ApiModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        ActivityModules::class,
        FragmentModules::class,
        ViewModelModules::class,
        AndroidSupportInjectionModule::class
    ]
)

interface AppComponent: AndroidInjector<IStationApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: IStationApplication): AppComponent
    }
//    fun inject(application: Application)
}