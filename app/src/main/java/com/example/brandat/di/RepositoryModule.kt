package com.example.brandat.di

import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.data.repos.products.ProductsRepository
import com.example.brandat.data.repos.user.IUserRepository
import com.example.brandat.data.repos.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideProductRepository(
        repository: ProductsRepository
    ):IProductsRepository

    @Binds
    fun provideUserRepository(
        repository: UserRepository
    ):IUserRepository
}