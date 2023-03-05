package com.dealtaskmobile.audiorecordervk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dealtaskmobile.domain.usercase.AddAudioNote
import com.dealtaskmobile.domain.usercase.DeleteAudioNote
import com.dealtaskmobile.domain.usercase.GetNoteList
import com.dealtaskmobile.domain.usercase.SetAccessTokenVK

class AudioNoteViewModelFactory(

    private val addAudioNote: AddAudioNote,
    private val getNoteList: GetNoteList,
    private val deleteAudioNote: DeleteAudioNote,
    private val setAccessTokenVK: SetAccessTokenVK
)


    : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AudioNoteViewModel(
            addAudioNote = addAudioNote,
            getNoteList = getNoteList,
            deleteAudioNote=deleteAudioNote,
            setAccessTokenVK = setAccessTokenVK,
        ) as T
    }
}