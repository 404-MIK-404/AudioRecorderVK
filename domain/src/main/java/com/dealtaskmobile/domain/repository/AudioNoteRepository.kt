package com.dealtaskmobile.domain.repository

import com.dealtaskmobile.domain.models.AudioNoteParams

interface AudioNoteRepository {

    fun addAudioNote(params: AudioNoteParams)

    fun getNoteList() : List<AudioNoteParams>

    fun removeAudioNote(params: AudioNoteParams)

}