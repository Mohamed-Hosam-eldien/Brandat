package com.example.brandat.di

import android.content.Context
import androidx.room.Room
import com.example.brandat.data.source.local.db.BrandatDao
import com.example.brandat.data.source.local.db.BrandatDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModules {

    @Provides
    //@Singleton
    fun provideDatabase(@ApplicationContext context: Context): BrandatDataBase =
        Room.databaseBuilder(
            context,
            BrandatDataBase::class.java,
            "Brandat-DB"
        ).build()

    @Provides
   // @Singleton
    fun provideBrandatDao(db: BrandatDataBase): BrandatDao = db.brandatDao()

}










