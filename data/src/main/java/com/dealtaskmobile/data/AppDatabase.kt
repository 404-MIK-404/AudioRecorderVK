package com.dealtaskmobile.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dealtaskmobile.data.dao.AudioNoteDao
import com.dealtaskmobile.data.entity.AudioNote


@Database(entities= arrayOf(AudioNote::class), version= 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun audioRecordDao() : AudioNoteDao


}