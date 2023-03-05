package com.dealtaskmobile.domain.usercase

import com.dealtaskmobile.domain.models.AudioNoteParams
import com.dealtaskmobile.domain.repository.AudioNoteRepository

class DeleteAudioNote(private val audioNoteRepository: AudioNoteRepository) {

    fun execute(audioNoteParams: AudioNoteParams){
        audioNoteRepository.removeAudioNote(params = audioNoteParams)
    }

}