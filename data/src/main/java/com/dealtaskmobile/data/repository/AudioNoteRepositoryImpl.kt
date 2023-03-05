package com.dealtaskmobile.data.repository

import com.dealtaskmobile.data.AppDatabase
import com.dealtaskmobile.data.entity.AudioNote
import com.dealtaskmobile.domain.models.AudioNoteParams
import com.dealtaskmobile.domain.repository.AudioNoteRepository

class AudioNoteRepositoryImpl(private val db: AppDatabase) : AudioNoteRepository {


    override fun addAudioNote(params: AudioNoteParams) {
        db.audioRecordDao().addAudioNote(AudioNote(nameFile = params.nameFile, dateEnd = params.dateEnd,
            namePath = params.namePath, timestamp = params.timestamp,
            duration = params.duration))
    }

    override fun getNoteList() : List<AudioNoteParams> {
        return db.audioRecordDao().getAll()
    }

    override fun removeAudioNote(params: AudioNoteParams) {
       db.audioRecordDao().deleteAudioNote(id = params.id).toString()
    }


}