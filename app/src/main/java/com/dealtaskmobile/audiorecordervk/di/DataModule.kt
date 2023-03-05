package com.dealtaskmobile.audiorecordervk.di

import android.content.Context
import androidx.room.Room
import com.dealtaskmobile.data.AppDatabase
import com.dealtaskmobile.data.repository.AudioNoteRepositoryImpl
import com.dealtaskmobile.data.repository.VKRepositoryImpl
import com.dealtaskmobile.data.sharedprefs.SharedPrefTokenVK
import com.dealtaskmobile.data.storage.StorageVK
import com.dealtaskmobile.domain.repository.AudioNoteRepository
import com.dealtaskmobile.domain.repository.VKRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {


    @Provides
    fun provideAppDatabase(context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "audio_note_app")
            .build()
    }


    @Provides
    fun provideAudioNoteRepository(db: AppDatabase): AudioNoteRepository{
        return AudioNoteRepositoryImpl(db=db)
    }


    @Provides
    fun provideStorageVK(context: Context): StorageVK{
        return SharedPrefTokenVK(context=context)
    }

    @Provides
    fun provideVKRepository(storageVK: StorageVK): VKRepository{
        return VKRepositoryImpl(storageVK = storageVK)
    }


}