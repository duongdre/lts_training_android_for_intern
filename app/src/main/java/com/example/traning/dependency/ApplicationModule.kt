package com.example.traning.dependency

import android.app.Application
import com.dds.istation.IStationApplication
import dagger.Binds
import dagger.Module

@Module
internal abstract class ApplicationModule {

    @Binds
    abstract fun application(application: IStationApplication): Application

}