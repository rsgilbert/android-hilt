package com.example.android.hilt.di

import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.data.LoggerInMemoryDataSource
import com.example.android.hilt.data.LoggerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class DatabaseLogger

@Qualifier
annotation class InMemoryLogger

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggerDatabaseModule {
    @DatabaseLogger
    @Singleton
    @Binds
    abstract fun bindLoggerDataSource(impl: LoggerLocalDataSource) : LoggerDataSource
}


@InstallIn(ActivityComponent::class)
@Module
abstract class LoggerInMemoryModule {

    @ActivityScoped
    @Binds
    @InMemoryLogger
    abstract fun bindLoggerDataSource(impl: LoggerInMemoryDataSource) : LoggerDataSource

}