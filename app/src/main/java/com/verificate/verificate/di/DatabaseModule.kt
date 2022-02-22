package com.verificate.verificate.di

import android.content.Context
import androidx.room.Room
import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.database.VerificateRoomDatabase
import com.verificate.verificate.database.dao.VerificationDao
import com.verificate.verificate.framework.datasource.remote.ApiService
import com.verificate.verificate.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    //Database dependency
    @Provides
    @Singleton
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return Constants.DATABASE_NAME
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): VerificateRoomDatabase {
        return Room.databaseBuilder(context, VerificateRoomDatabase::class.java, provideDatabaseName()).fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideLocalStorage(context: Context): LocalStorage {
        return LocalStorage(context = context )
    }

    //DAO

    @Provides
    @Singleton
    fun provideVerificationDao(appDatabase: VerificateRoomDatabase): VerificationDao {
        return appDatabase.verificationDao()
    }

}
