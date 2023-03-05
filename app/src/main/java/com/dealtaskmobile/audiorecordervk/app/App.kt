package com.dealtaskmobile.audiorecordervk.app

import android.app.Application
import com.dealtaskmobile.audiorecordervk.di.AppComponent
import com.dealtaskmobile.audiorecordervk.di.AppModule
import com.dealtaskmobile.audiorecordervk.di.DaggerAppComponent
import com.vk.api.sdk.VK

class App : Application() {


    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        VK.initialize(this)
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this)).build()
    }
}