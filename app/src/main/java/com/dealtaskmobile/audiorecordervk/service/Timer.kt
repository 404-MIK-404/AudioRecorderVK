package com.dealtaskmobile.audiorecordervk.service

import android.os.Looper


class Timer(listener: OnTimerTickListener) {

    interface OnTimerTickListener{
        fun onTimerTick(duration: String)
    }

    private var handler = android.os.Handler(Looper.getMainLooper())
    private  lateinit var  runnable: Runnable

    private var duration = 0L
    private var delay  = 100L

    init {
        runnable = Runnable{
            duration += delay
            handler.postDelayed(runnable,delay)
            listener.onTimerTick(duration = timeFormat())
        }
    }

    fun startTimer(){
        handler.postDelayed(runnable,delay)
    }

    fun stopTimer(){
        handler.removeCallbacks(runnable)
        duration = 0L
    }

    fun timeFormat(): String{
        val millis = (duration % 1000)/10
        val seconds = (duration / 1000) % 60
        val minutes = (duration / (1000 * 60)) % 60
        val hours = (duration/ (1000 * 60 * 60))

        return if (hours > 0) "%02d:%02d:%02d.%02d".format(hours,minutes,seconds,millis)
               else "%02d:%02d.%02d".format(minutes,seconds,millis)


    }
}