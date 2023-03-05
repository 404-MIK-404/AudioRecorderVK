package com.dealtaskmobile.domain.models

data class AudioNoteParams(
    val id: Int = 0,
    val nameFile: String,
    val dateEnd: String,
    val namePath: String,
    val timestamp: Long,
    var duration: String, )