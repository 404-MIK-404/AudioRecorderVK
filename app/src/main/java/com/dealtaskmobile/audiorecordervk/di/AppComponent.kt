package com.dealtaskmobile.audiorecordervk.di

import com.dealtaskmobile.audiorecordervk.screens.HomeActivity
import com.dealtaskmobile.audiorecordervk.screens.SignInActivity
import com.dealtaskmobile.audiorecordervk.screens.VKFormActivity
import dagger.Component

@Component(modules = [AppModule::class,DataModule::class,DomainModule::class])
interface AppComponent {

    fun inject(signInActivity: SignInActivity)
    fun inject(VKFormActivity: VKFormActivity)
    fun inject(home: HomeActivity)

}