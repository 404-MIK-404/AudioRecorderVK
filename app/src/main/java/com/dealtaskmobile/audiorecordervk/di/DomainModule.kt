package com.dealtaskmobile.audiorecordervk.di

import com.dealtaskmobile.domain.repository.AudioNoteRepository
import com.dealtaskmobile.domain.repository.VKRepository
import com.dealtaskmobile.domain.usercase.AddAudioNote
import com.dealtaskmobile.domain.usercase.DeleteAudioNote
import com.dealtaskmobile.domain.usercase.GetNoteList
import com.dealtaskmobile.domain.usercase.SetAccessTokenVK
import dagger.Module
import dagger.Provides


@Module
class DomainModule {

    @Provides
    fun provideAddAudioNote(audioNoteRepository: AudioNoteRepository) : AddAudioNote{
        return AddAudioNote(audioNoteRepository = audioNoteRepository)
    }


    @Provides
    fun provideGetNoteList(audioNoteRepository: AudioNoteRepository) : GetNoteList{
        return GetNoteList(audioNoteRepository= audioNoteRepository)
    }

    @Provides
    fun provideDeleteAudioNote(audioNoteRepository: AudioNoteRepository) : DeleteAudioNote{
        return DeleteAudioNote(audioNoteRepository=audioNoteRepository)
    }

    @Provides
    fun provideSetAccessTokenVK(vkRepository: VKRepository): SetAccessTokenVK{
        return SetAccessTokenVK(vkRepository = vkRepository)
    }


}