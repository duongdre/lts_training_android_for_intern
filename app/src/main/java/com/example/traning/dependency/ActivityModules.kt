package com.example.traning.dependency

import com.example.traning.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModules {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}