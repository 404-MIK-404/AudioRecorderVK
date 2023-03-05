package com.dealtaskmobile.audiorecordervk.managers.recordmanager

import android.media.MediaPlayer
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import com.dealtaskmobile.audiorecordervk.R
import com.dealtaskmobile.domain.models.AudioNoteParams

class MediaPlayerController {


    private var mediaPlayer: MediaPlayer? = null

    private var previousImagePlayer: ImageButton? = null

    private var previousPosition: Int = -1

    private var playerIsPlay = false


    private var handler = android.os.Handler(Looper.getMainLooper())
    private  lateinit var  runnable: Runnable

    private var duration = 0L
    private var delay  = 100L

   private fun initMediaPlayer(filePath: String){
        mediaPlayer = MediaPlayer()
        mediaPlayer?.apply {
            setDataSource(filePath)
            prepare()
        }
    }

    private fun start(playIconButton: ImageButton, position: Int){
        playerIsPlay = true
        previousPosition = position
        previousImagePlayer = playIconButton
        playIconButton.setImageResource(R.drawable.ic_baseline_pause_24)
        mediaPlayer?.start()

    }

    private fun stop(){
        playerIsPlay = false
        previousPosition = -1
        previousImagePlayer?.setImageResource(R.mipmap.play_audio_note)
        mediaPlayer?.stop()
        handler.removeCallbacks(runnable)
        duration = 0L
    }




    fun startPlayerNote(audioNoteParams: AudioNoteParams, playIconButton: ImageButton, position: Int,tvDuration: TextView){
        tvDuration.text = "00:00/${audioNoteParams.duration}"
        if (previousPosition == position){
            if (!playerIsPlay){
                start(playIconButton=playIconButton,position=position)
            } else {
                stop()
            }
        } else if (previousPosition == -1) {
            initMediaPlayer(filePath = audioNoteParams.namePath)
            start(playIconButton=playIconButton,position=position)
        } else {
            stop()
            initMediaPlayer(filePath = audioNoteParams.namePath)
            start(playIconButton=playIconButton,position=position)
        }


        mediaPlayer?.setOnCompletionListener{
            stop()
        }
        mediaPlayer?.setOnPreparedListener{
            updateDurationPlayer(durationEnd = audioNoteParams.duration, tvDuration=tvDuration)
        }
    }


    private fun updateDurationPlayer(durationEnd: String,tvDuration: TextView){
        runnable = Runnable{
            duration += delay
            handler.postDelayed(runnable,delay)
            tvDuration.text = "${timeFormat()}/${durationEnd}"
        }
        handler.postDelayed(runnable,delay)
    }


    fun timeFormat(): String{
        val seconds = (duration / 1000) % 60
        val minutes = (duration / (1000 * 60)) % 60
        val hours = (duration/ (1000 * 60 * 60))

        return if (hours > 0) "%02d:%02d:%02d.%02d".format(hours,minutes,seconds)
        else "%02d:%02d".format(minutes,seconds)


    }


}