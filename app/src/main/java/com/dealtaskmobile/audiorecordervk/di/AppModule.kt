package com.dealtaskmobile.audiorecordervk.di

import android.content.Context
import com.dealtaskmobile.audiorecordervk.viewmodel.AudioNoteViewModelFactory
import com.dealtaskmobile.domain.usercase.AddAudioNote
import com.dealtaskmobile.domain.usercase.DeleteAudioNote
import com.dealtaskmobile.domain.usercase.GetNoteList
import com.dealtaskmobile.domain.usercase.SetAccessTokenVK
import dagger.Module
import dagger.Provides


@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideAudioNoteViewModelFactory(
        addAudioNote: AddAudioNote,
        getNoteList: GetNoteList,
        deleteAudioNote: DeleteAudioNote,
        setAccessTokenVK: SetAccessTokenVK,
        )

    : AudioNoteViewModelFactory {

        return AudioNoteViewModelFactory(
            addAudioNote = addAudioNote,
            getNoteList = getNoteList,
            deleteAudioNote = deleteAudioNote,
            setAccessTokenVK = setAccessTokenVK
        )
    }



}