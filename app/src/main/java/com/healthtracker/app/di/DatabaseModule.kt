package com.healthtracker.app.di

import android.content.Context
import androidx.room.Room
import com.healthtracker.app.data.local.AppDatabase
import com.healthtracker.app.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
    
    @Provides
    fun provideHealthLogDao(database: AppDatabase): HealthLogDao = database.healthLogDao()
    
    @Provides
    fun provideVitalRecordDao(database: AppDatabase): VitalRecordDao = database.vitalRecordDao()
    
    @Provides
    fun provideSleepRecordDao(database: AppDatabase): SleepRecordDao = database.sleepRecordDao()
    
    @Provides
    fun provideActivityRecordDao(database: AppDatabase): ActivityRecordDao = database.activityRecordDao()
    
    @Provides
    fun provideMedicationDao(database: AppDatabase): MedicationDao = database.medicationDao()
    
    @Provides
    fun provideDocumentDao(database: AppDatabase): DocumentDao = database.documentDao()
}
