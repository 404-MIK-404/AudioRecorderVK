package com.dealtaskmobile.domain.usercase

import com.dealtaskmobile.domain.models.AudioNoteParams
import com.dealtaskmobile.domain.repository.AudioNoteRepository

class GetNoteList(private val audioNoteRepository: AudioNoteRepository) {

    fun execute() : List<AudioNoteParams> {
        return audioNoteRepository.getNoteList()
    }
}