package com.nextsolutions.keyysafe.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.nextsolutions.keyysafe.analytics.data.repository.IAnalyticsRepository
import com.nextsolutions.keyysafe.analytics.domain.repository.AnalyticsRepository
import com.nextsolutions.keyysafe.archive.data.repository.IArchiveRepository
import com.nextsolutions.keyysafe.archive.domain.repository.ArchiveRepository
import com.nextsolutions.keyysafe.auth.data.repository.IAuthRepository
import com.nextsolutions.keyysafe.auth.domain.repository.AuthRepository
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreManager
import com.nextsolutions.keyysafe.common.data.preferences.PreferencesManager
import com.nextsolutions.keyysafe.common.password_manager.PasswordEncrypt
import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.db.database.KeySafeDatabase
import com.nextsolutions.keyysafe.entry.data.repository.ICreateEditRepository
import com.nextsolutions.keyysafe.entry.domain.repository.CreateEditRepository
import com.nextsolutions.keyysafe.labels.data.repository.ILabelRepository
import com.nextsolutions.keyysafe.labels.domain.repository.LabelRepository
import com.nextsolutions.keyysafe.main.data.repository.IDashboardRepository
import com.nextsolutions.keyysafe.main.domain.repository.DashboardEntryRepository
import com.nextsolutions.keyysafe.main.domain.use_cases.PasswordGeneratorUseCase
import com.nextsolutions.keyysafe.settings.ui.Database.data.repository.IBackUpRepository
import com.nextsolutions.keyysafe.settings.ui.Database.data.repository.IRestoreRepository
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.BackUpRepository
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.RestoreRepository
import com.nextsolutions.keyysafe.trash.data.repository.ITrashRepository
import com.nextsolutions.keyysafe.trash.domain.repository.TrashRepository
import com.nextsolutions.keyysafe.use_cases.CalculatePercentageOfTotalEntriesUseCase
import com.nextsolutions.keyysafe.use_cases.PasswordAnalyzerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.security.KeyStore
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


    @Singleton
    @Provides
    fun provideAuthRepository(
        prefs: PreferencesManager
    ) : AuthRepository{
        return IAuthRepository(prefs)
    }

    @Singleton
    @Provides
    fun providePasswordEncryptor() : PasswordEncrypt{
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val secretKeyEntry = keyStore.getEntry("key_safe_key_alias", null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey
        return PasswordEncrypt(secretKey)
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): KeySafeDatabase {
        return Room.databaseBuilder(
            app,
            KeySafeDatabase::class.java,
            KeySafeDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDatabaseDao(db: KeySafeDatabase): DataAccessObject {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideCreateEditRepository(dao: DataAccessObject) : CreateEditRepository {
        return ICreateEditRepository(dao)
    }

    @Provides
    @Singleton
    fun provideLabelRepository(dao: DataAccessObject) : LabelRepository {
        return ILabelRepository(dao)
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(dao: DataAccessObject) : DashboardEntryRepository {
        return IDashboardRepository(dao)
    }

    @Provides
    @Singleton
    fun provideArchiveRepository(dao: DataAccessObject) : ArchiveRepository {
        return IArchiveRepository(dao)
    }

    @Provides
    @Singleton
    fun provideAnalyticsRepository(dao: DataAccessObject) : AnalyticsRepository {
        return IAnalyticsRepository(dao)
    }

    @Provides
    @Singleton
    fun provideTrashRepository(dao: DataAccessObject) : TrashRepository {
        return ITrashRepository(dao)
    }

    @Provides
    @Singleton
    fun provideBackUpRepository(dao: DataAccessObject, @ApplicationContext context: Context) : BackUpRepository {
        return IBackUpRepository(dao, context)
    }

    @Provides
    @Singleton
    fun provideRestoreRepository(dao: DataAccessObject, @ApplicationContext context: Context,) : RestoreRepository {
        return IRestoreRepository(context, dao)
    }


    @Provides
    @Singleton
    fun providePasswordAnalyzerUseCase() = PasswordAnalyzerUseCase()

    @Provides
    @Singleton
    fun provideCalculatePercentageOfTotalEntriesUseCase() = CalculatePercentageOfTotalEntriesUseCase()

    @Provides
    @Singleton
    fun providePasswordGeneratorUseCase() = PasswordGeneratorUseCase()

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}