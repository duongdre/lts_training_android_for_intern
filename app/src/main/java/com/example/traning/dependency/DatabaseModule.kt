package com.example.traning.dependency

import android.app.Application
import androidx.room.Room
import com.dds.istation.model.dao.AppDatabase
import com.dds.istation.model.dao.UserDAO
import com.dds.istation.utils.Const
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    @Named("app_db")
    fun database(application: Application) = Room
        .databaseBuilder(application, AppDatabase::class.java, Const.DATABASE_NAME)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun userDAO(@Named("app_db") database: AppDatabase): UserDAO = database.userDAO()
}