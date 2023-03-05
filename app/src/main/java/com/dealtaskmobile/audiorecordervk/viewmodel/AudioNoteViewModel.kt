package com.dealtaskmobile.audiorecordervk.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dealtaskmobile.data.entity.AudioNote
import com.dealtaskmobile.domain.models.AudioNoteParams
import com.dealtaskmobile.domain.usercase.AddAudioNote
import com.dealtaskmobile.domain.usercase.DeleteAudioNote
import com.dealtaskmobile.domain.usercase.GetNoteList
import com.dealtaskmobile.domain.usercase.SetAccessTokenVK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AudioNoteViewModel(

    private val addAudioNote: AddAudioNote,
    private val getNoteList: GetNoteList,
    private val deleteAudioNote: DeleteAudioNote,
    private val setAccessTokenVK: SetAccessTokenVK,

) : ViewModel() {


    private val listAudioNote = MutableLiveData<List<AudioNoteParams>>()

    fun getListAudioNote() : LiveData<List<AudioNoteParams>>{
        return listAudioNote
    }


    init {
        Log.e("ViewModel Event: ", " ViewModel is created")
    }


    fun sendNoteToAdd(audioNoteParams: AudioNoteParams){
        CoroutineScope(Dispatchers.IO).launch {
            addAudioNote.execute(audioNoteParams = audioNoteParams)
        }
    }

    fun sendGetNoteList(){
        CoroutineScope(Dispatchers.IO).launch {
            listAudioNote.postValue(getNoteList.execute())
        }
    }

    fun deleteAudioNote(audioNoteParams: AudioNoteParams){
        CoroutineScope(Dispatchers.IO).launch {
            deleteAudioNote.execute(audioNoteParams=audioNoteParams)
        }
    }

    fun saveTkVK(tkVK: String){
        setAccessTokenVK.execute(tkVK = tkVK)
    }


}