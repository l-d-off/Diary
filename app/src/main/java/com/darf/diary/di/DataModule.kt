package com.darf.diary.di

import android.app.Application
import com.darf.diary.data.database.AppDatabase
import com.darf.diary.data.database.EventDao
import com.darf.diary.data.network.ApiService
import com.darf.diary.data.repository.EventRepositoryImpl
import com.darf.diary.domain.EventRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindEventRepository(impl: EventRepositoryImpl): EventRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideEventDao(application: Application): EventDao {
            return AppDatabase.getInstance(application).eventDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService (): ApiService {
            return ApiService
        }
    }
} 