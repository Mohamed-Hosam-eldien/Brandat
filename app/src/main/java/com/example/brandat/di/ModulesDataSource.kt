package com.example.brandat.di

import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.local.LocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.data.source.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ModulesDataSource {
    @Binds
    fun provideLocalDataSource(localData:LocalDataSource):ILocalDataSource


    @Binds
    fun provideRemoteDataSource(localRemote:RemoteDataSource):IRemoteDataSource

}
