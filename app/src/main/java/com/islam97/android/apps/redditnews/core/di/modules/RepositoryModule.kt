package com.islam97.android.apps.redditnews.core.di.modules

import com.islam97.android.apps.redditnews.data.repositories.AppRepositoryImpl
import com.islam97.android.apps.redditnews.domain.repositories.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideAppRepository(repository: AppRepositoryImpl): AppRepository {
        return repository
    }
}