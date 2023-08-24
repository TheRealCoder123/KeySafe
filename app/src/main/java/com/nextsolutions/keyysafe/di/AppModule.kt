package com.nextsolutions.keyysafe.di

import android.content.Context
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(
        @ApplicationContext context: Context
    ) = PreferencesManager(context)

    @Singleton
    @Provides
    fun provideDataStorePreferencesManager(
        @ApplicationContext context: Context
    ) = DataStoreManager(context)


}