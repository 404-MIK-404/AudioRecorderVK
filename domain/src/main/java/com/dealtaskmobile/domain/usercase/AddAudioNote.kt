package com.dealtaskmobile.domain.usercase

import com.dealtaskmobile.domain.models.AudioNoteParams
import com.dealtaskmobile.domain.repository.AudioNoteRepository

class AddAudioNote(private val audioNoteRepository: AudioNoteRepository) {

    fun execute(audioNoteParams: AudioNoteParams){
        audioNoteRepository.addAudioNote(params = audioNoteParams)
    }

}