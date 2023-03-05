package com.dealtaskmobile.audiorecordervk.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dealtaskmobile.audiorecordervk.R
import com.dealtaskmobile.audiorecordervk.app.App
import com.dealtaskmobile.audiorecordervk.databinding.SignInBinding
import com.dealtaskmobile.audiorecordervk.viewmodel.AudioNoteViewModel
import com.dealtaskmobile.audiorecordervk.viewmodel.AudioNoteViewModelFactory
import javax.inject.Inject


class SignInActivity : AppCompatActivity() {

    private var binding: SignInBinding? = null

    private var btnSignVK : Button? = null

    private var btnSignToApp: Button? = null

    @Inject
    lateinit var vmFactory: AudioNoteViewModelFactory

    private var vm: AudioNoteViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInBinding.inflate(layoutInflater)
        setContentView(R.layout.sign_in)
        init()
        initVM()
    }

    private fun initVM(){
        (applicationContext as App).appComponent!!.inject(this)
        vm = ViewModelProvider(this,vmFactory).get(AudioNoteViewModel::class.java)
    }



    private fun init(){
        btnSignVK = findViewById(R.id.btnSignVK)
        btnSignToApp = findViewById(R.id.btnSignToApp)
        btnSignVK?.setOnClickListener(View.OnClickListener { view ->
            startActivity(Intent(this@SignInActivity, VKFormActivity::class.java))
        })
        btnSignToApp?.setOnClickListener{
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
        }
    }





}

