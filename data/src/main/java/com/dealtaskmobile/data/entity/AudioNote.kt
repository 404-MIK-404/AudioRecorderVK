package com.dealtaskmobile.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "AUDIO_NOTE")
data class AudioNote(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val nameFile: String,
    val dateEnd: String,
    val namePath: String,
    val timestamp: Long,
    var duration: String,
    ) {


}