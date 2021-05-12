package com.raantech.solalat.user.data.di

import android.content.Context
import androidx.room.Room
import com.raantech.solalat.user.data.db.ApplicationDB
import com.raantech.solalat.user.data.db.ApplicationDB.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): ApplicationDB {
        return Room.databaseBuilder(
            context,
            ApplicationDB::class.java,
            DATABASE_NAME
        ).build()
    }
}