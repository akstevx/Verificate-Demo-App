package com.verificate.verificate.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    //Repository dependency
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context = appContext

}