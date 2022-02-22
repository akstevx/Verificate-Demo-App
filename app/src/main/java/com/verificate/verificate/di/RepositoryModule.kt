package com.verificate.verificate.di

import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.database.dao.VerificationDao
import com.verificate.verificate.framework.datasource.local.VerificationDatasource
import com.verificate.verificate.framework.datasource.local.VerificationDatasourceImpl
import com.verificate.verificate.framework.datasource.remote.ApiService
import com.verificate.verificate.framework.datasource.remote.StatesServiceApi
import com.verificate.verificate.framework.datasource.repository.abstraction.AgentRepository
import com.verificate.verificate.framework.datasource.repository.abstraction.AuthRepository
import com.verificate.verificate.framework.datasource.repository.abstraction.VerificationRepository
import com.verificate.verificate.framework.datasource.repository.implementation.AgentRepositoryImpl
import com.verificate.verificate.framework.datasource.repository.implementation.AuthRepositoryImpl
import com.verificate.verificate.framework.datasource.repository.implementation.VerificationRepositoryImpl
import com.verificate.verificate.services.BackgroundService
import com.verificate.verificate.services.BackgroundServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //Repository dependency
    @Provides
    @Singleton
    fun provideAuthRepository(apiService : ApiService, statesServiceApi : StatesServiceApi, localStorage: LocalStorage):
            AuthRepository = AuthRepositoryImpl(apiService, statesServiceApi,localStorage)

    @Provides
    @Singleton
    fun provideAgentRepository(apiService : ApiService, localStorage: LocalStorage):
            AgentRepository = AgentRepositoryImpl(apiService = apiService, localStorage = localStorage)

    @Provides
    @Singleton
    fun provideVerificationRepository(apiService : ApiService, localStorage: LocalStorage, verificationDatasource: VerificationDatasource):
            VerificationRepository = VerificationRepositoryImpl(apiService = apiService, localStorage = localStorage, verificationDatasource = verificationDatasource)

    @Provides
    @Singleton
    fun provideBackgroundService(): BackgroundService = BackgroundServiceImpl()

    // Datasource dependency
    @Provides
    @Singleton
    fun provideVerificationDatasource(verificationDao: VerificationDao):
            VerificationDatasource = VerificationDatasourceImpl(verificationDao = verificationDao)

}