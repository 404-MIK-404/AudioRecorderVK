package com.dealtaskmobile.audiorecordervk.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dealtaskmobile.audiorecordervk.R
import com.dealtaskmobile.audiorecordervk.app.App
import com.dealtaskmobile.audiorecordervk.databinding.ActivityWebBinding
import com.dealtaskmobile.audiorecordervk.viewmodel.AudioNoteViewModel
import com.dealtaskmobile.audiorecordervk.viewmodel.AudioNoteViewModelFactory
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class VKFormActivity : AppCompatActivity() {


    private var binding: ActivityWebBinding? = null


    @Inject
    lateinit var vmFactory: AudioNoteViewModelFactory

    private var vm: AudioNoteViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_web)
        initVM()
        initVK()
    }


    private fun initVM(){
        (applicationContext as App).appComponent!!.inject(this)
        vm = ViewModelProvider(this,vmFactory).get(AudioNoteViewModel::class.java)
    }


    private fun changeActivity(intent: Intent){
        startActivity(intent)
    }


    private fun initVK(){
        val authLauncher = VK.login(this@VKFormActivity) { result : VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    vm?.saveTkVK(result.token.accessToken)
                    Toast.makeText(this@VKFormActivity,"Авторизация прошла успешно\nДобро пожаловать !",Toast.LENGTH_SHORT).show()
                    changeActivity(Intent(this@VKFormActivity, HomeActivity::class.java))
                }
                is VKAuthenticationResult.Failed -> {
                    Toast.makeText(this@VKFormActivity,"Авторизация прошла не успешно.\nПожалуйста повторите попытку.",Toast.LENGTH_SHORT).show()
                    changeActivity(Intent(this@VKFormActivity, SignInActivity::class.java))
                }
            }
        }
        authLauncher.launch(arrayListOf(VKScope.DOCS,VKScope.OFFLINE))
    }


}