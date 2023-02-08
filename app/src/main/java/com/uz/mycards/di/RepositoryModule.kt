package com.uz.mycards.di

import com.uz.mycards.data.repository.AppRepository
import com.uz.mycards.data.repository.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun getAppRepository(impl: AppRepositoryImpl): AppRepository
}