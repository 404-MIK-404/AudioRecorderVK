package com.dealtaskmobile.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dealtaskmobile.data.entity.AudioNote
import com.dealtaskmobile.domain.models.AudioNoteParams

@Dao
interface AudioNoteDao {

    @Query("SELECT * FROM AUDIO_NOTE")
    fun getAll(): List<AudioNoteParams>

    @Insert
    fun addAudioNote(vararg audioNote: AudioNote)


    @Query("DELETE FROM AUDIO_NOTE WHERE id = :id")
    fun deleteAudioNote(id: Int) : Int



}