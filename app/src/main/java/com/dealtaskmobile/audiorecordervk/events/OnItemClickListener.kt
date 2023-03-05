package com.dealtaskmobile.audiorecordervk.events

import android.widget.ImageButton
import android.widget.TextView
import com.dealtaskmobile.domain.models.AudioNoteParams

interface OnItemClickListener {
    fun onItemClickListener(imgBtn: ImageButton, tvDuration: TextView, audioNoteParams: AudioNoteParams, position: Int)
}