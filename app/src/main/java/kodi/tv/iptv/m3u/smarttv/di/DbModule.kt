package kodi.tv.iptv.m3u.smarttv.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kodi.tv.iptv.m3u.smarttv.db.KodiDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, KodiDatabase::class.java, "kst232"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: KodiDatabase) = db.channelModelDao()

/*
    @Provides
    fun provideEntity() = NoteModel()
*/

}