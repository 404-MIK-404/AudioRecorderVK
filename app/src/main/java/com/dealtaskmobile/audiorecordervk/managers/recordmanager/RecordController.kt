package com.dealtaskmobile.audiorecordervk.managers.recordmanager

import android.annotation.SuppressLint
import android.media.MediaRecorder
import com.dealtaskmobile.domain.models.AudioNoteParams
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class RecordController {

    private var mediaRecorder: MediaRecorder? = null
    private var dirPath = ""
    private var fileName = ""
    var timeEndAudioNote = Calendar.getInstance()
    var duration = ""
    set(value) {field=value}


    private var simpleDateFormat = SimpleDateFormat("yyyy.MM.dd_hh.mm.ss")

    private var dateFormat = SimpleDateFormat("yyyy.MM.dd hh.mm.ss")

    fun stopRecord(){
        mediaRecorder?.stop()
    }


    @SuppressLint("NewApi")
    fun resumeRecorder(textInputEditText: TextInputEditText): Boolean{
        stopRecord()
        timeEndAudioNote = Calendar.getInstance()
        textInputEditText.setText(fileName)
        return false
    }



    fun startRecord(pathFile: String?): Boolean{
        mediaRecorder = MediaRecorder()
        dirPath = "${pathFile}/"
        fileName = "AudiNote_${simpleDateFormat.format(Date())}"
        mediaRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPath$fileName.mp4")
            try {
                prepare()
            } catch (e: IOException){ }

            start()
        }
        return true
    }

    fun saveRecord(textInputEditText: TextInputEditText) : AudioNoteParams {
        val newFileName = textInputEditText.text.toString()
        if (newFileName != fileName){
            var newFile = File("$dirPath$newFileName.mp4")
            File("$dirPath$fileName.mp4").renameTo(newFile)
        }
        return AudioNoteParams(nameFile = newFileName, dateEnd = dateFormat.format(timeEndAudioNote.time)
            , namePath = "$dirPath$newFileName.mp4",timestamp=Date().time,
            duration=duration)
    }


    fun deleteRecord(){
        File("$dirPath$fileName.mp4").delete()
    }

    fun deleteRecord(dirPath: String){
        File("$dirPath").delete()
    }


}