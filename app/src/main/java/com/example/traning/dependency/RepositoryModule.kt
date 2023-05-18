package com.example.traning.dependency

import com.dds.istation.model.repository.auth.AuthRepository
import com.dds.istation.model.repository.auth.AuthRepositoryImpl
import com.dds.istation.model.repository.profile.ProfileRepository
import com.dds.istation.model.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun authRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun profileRepository(profileRepository: ProfileRepositoryImpl): ProfileRepository

}