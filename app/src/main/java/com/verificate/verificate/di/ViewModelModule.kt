package com.verificate.verificate.di

import android.content.Context
import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.framework.datasource.repository.abstraction.AuthRepository
import com.verificate.verificate.framework.presentation.viewmodels.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    @Singleton
    fun provideAuthViewModel (authRepository: AuthRepository, appContext: Context, localStorage: LocalStorage): AuthViewModel =
        AuthViewModel(authRepository = authRepository, appContext = appContext, localStorage = localStorage)

}