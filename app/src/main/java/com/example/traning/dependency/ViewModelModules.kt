package com.example.traning.dependency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dds.istation.viewmodel.*
import com.dds.istation.viewmodel.common.ViewModelFactory
import com.dds.istation.viewmodel.common.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModules {

    @Binds
    internal abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel::class)
    internal abstract fun authenticationViewModel(authenticationViewModel: AuthenticationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BasicInfoViewModel::class)
    internal abstract fun basicInfoViewModel(basicInfoViewModel: BasicInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel
}