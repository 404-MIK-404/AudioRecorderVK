package com.dealtaskmobile.audiorecordervk.screens

import android.app.DownloadManager.Request
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dealtaskmobile.audiorecordervk.R
import com.dealtaskmobile.audiorecordervk.adapters.NoteAudioAdapter
import com.dealtaskmobile.audiorecordervk.app.App
import com.dealtaskmobile.audiorecordervk.databinding.ActivityHomeBinding
import com.dealtaskmobile.audiorecordervk.events.OnItemClickListener
import com.dealtaskmobile.audiorecordervk.managers.alterdialogmanager.AlterDialogController
import com.dealtaskmobile.audiorecordervk.managers.recordmanager.MediaPlayerController
import com.dealtaskmobile.audiorecordervk.managers.recordmanager.RecordController
import com.dealtaskmobile.audiorecordervk.service.Timer
import com.dealtaskmobile.audiorecordervk.viewmodel.AudioNoteViewModel
import com.dealtaskmobile.audiorecordervk.viewmodel.AudioNoteViewModelFactory
import com.dealtaskmobile.domain.models.AudioNoteParams
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vk.api.sdk.*
import com.vk.api.sdk.exceptions.VKApiCodes
import com.vk.api.sdk.requests.VKRequest
import com.vk.api.sdk.utils.VKUrlResolver
import com.vk.sdk.api.base.dto.BaseUploadServer
import com.vk.sdk.api.docs.DocsService
import com.vk.sdk.api.docs.dto.DocsDoc
import com.vk.sdk.api.docs.dto.DocsGetMessagesUploadServerType
import com.vk.sdk.api.docs.dto.DocsSaveResponse
import com.vk.sdk.api.video.VideoService
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.audio_note_item.view.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import java.io.File
import javax.inject.Inject


const val REQUEST_CODE = 200

const val TAG = "HOME_ACTIVITY_EVENT"


class HomeActivity : AppCompatActivity(),Timer.OnTimerTickListener, OnItemClickListener{

    @Inject
    lateinit var vmFactory: AudioNoteViewModelFactory

    private var vm: AudioNoteViewModel? = null

    private var binding: ActivityHomeBinding? = null

    private var permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)

    private var permissionGranted = false

    private var timer: Timer = Timer(this)

    private var recordController: RecordController = RecordController()

    private var isRecording = false

    private var isPaused = false

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

    private var mediaPlayerController: MediaPlayerController = MediaPlayerController()

    private var alterDialogController: AlterDialogController = AlterDialogController()

    private val adapterNoteAudio = NoteAudioAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        (applicationContext as App).appComponent?.inject(this)
        vm = ViewModelProvider(this,vmFactory).get(AudioNoteViewModel::class.java)
        permissionGranted = ActivityCompat.checkSelfPermission(this,permissions[0]) == PackageManager.PERMISSION_GRANTED
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
        }
        init()
        initRecycleList()
        initChangeValue()
        initButton()
        initSearchView()
    }



    private fun initChangeValue(){
        vm?.getListAudioNote()?.observe(this, Observer {
            with(binding){
                adapterNoteAudio.addAudioNoteAll(it)
                changeModeEmptyList()
            }
        })

        vm?.sendGetNoteList()
    }


    private var callBackMethod = object : SimpleCallback(0,LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            when(direction){
                LEFT -> {

                    var yesEvent = object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            val audioNote = adapterNoteAudio.getAudioNoteParams(position=position)
                            vm?.deleteAudioNote(audioNoteParams = audioNote)
                            adapterNoteAudio.removeAudioNote(position = position)
                            Toast.makeText(this@HomeActivity,"Удаление произошло успешно !",Toast.LENGTH_SHORT).show()
                            recordController.deleteRecord(dirPath = audioNote.namePath)
                            changeModeEmptyList()
                        }
                    }

                    var noEvent = object: DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            Toast.makeText(this@HomeActivity,"Запись не удалена",Toast.LENGTH_SHORT).show()
                        }

                    }

                    alterDialogController.createAlterDialog(title = "Удаление записи",
                        message = "Вы точно хотите удалить данную запись ?\nНазвание записи: ${viewHolder.itemView.tvTitleName.text}",
                        positiveButton = "Да", negativeButton = "Нет", posButton = yesEvent,
                        negButton = noEvent,this@HomeActivity).show()

                    viewHolder.bindingAdapter?.notifyDataSetChanged()
                }
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            var recyclerDecoration = adapterNoteAudio.getRecycleDecoration(c = c,recyclerView=recyclerView, viewHolder = viewHolder, dX = dX, dY = dY, actionState = actionState,isCurrentlyActive=isCurrentlyActive, resources = resources)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }





    private fun init(){
        with(binding){

            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior?.peekHeight = 0
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

            recordAudioNote?.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    try{
                        when (event?.action) {
                            MotionEvent.ACTION_DOWN -> {
                                isRecording = recordController.startRecord(pathFile = externalCacheDir?.absolutePath)
                                isPaused = false
                                timer.startTimer()
                            }

                            MotionEvent.ACTION_UP -> {
                                listNoteAudio.visibility = View.VISIBLE
                                tvDurationRecord.visibility = View.GONE
                                timer.stopTimer()
                                isPaused = recordController.resumeRecorder(filenameInput)
                                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
                                bottomSheetBG.visibility = View.VISIBLE
                            }

                        }
                    } catch (ex: java.lang.RuntimeException){
                        Log.e(TAG,"Произошла ошибка в работе ${ex.message}")
                    } finally {
                        return v?.onTouchEvent(event) ?: true
                    }
                }
            })
        }
    }



    private fun listVisibilityChange(res: Int){
        tvImageListIsEmpty?.visibility = res
        tvTitleListIsEmpty?.visibility = res
    }


    private fun changeModeEmptyList(){
        if (adapterNoteAudio.itemCount == 0){
            listVisibilityChange(View.VISIBLE)
        } else {
            listVisibilityChange(View.GONE)
        }
    }

    private fun initRecycleList(){
        with(binding){
            listNoteAudio?.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.VERTICAL,false)
            listNoteAudio?.adapter = adapterNoteAudio
            changeModeEmptyList()
            var itemTouchHelper = ItemTouchHelper(callBackMethod)
            itemTouchHelper.attachToRecyclerView(listNoteAudio)
        }
    }


    private fun initButton(){
        with(binding){

            buttonCancel.setOnClickListener{
                recordController.deleteRecord()
                dismissEvent()
            }

            buttonSave.setOnClickListener{
                var audioObject = recordController.saveRecord(filenameInput)
                vm?.sendNoteToAdd(audioNoteParams = audioObject)
                dismissEvent()
                adapterNoteAudio.addAudioNote(audioNote = audioObject)
                changeModeEmptyList()
            }

            bottomSheetBG.setOnClickListener{
                recordController.deleteRecord()
                dismissEvent()
            }

        }

    }



    private val callBackOnQueryText = object: SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            with(binding){
                if (!newText.isNullOrBlank()){
                    adapterNoteAudio.filterList(query = newText!!)
                    titleAllNote?.text = "Поиск по запросу"
                } else {
                    titleAllNote?.text = getResources().getString(R.string.title_all_audio_note)
                    adapterNoteAudio.filterList(query = newText!!)
                }
            }
            return true
        }
    }



    private fun initSearchView(){
        with(binding){
            searchAudioNote.clearFocus()
            searchAudioNote.setOnQueryTextListener(callBackOnQueryText)
        }
    }

    private fun dismissEvent(){
        bottomSheetBG.visibility = View.GONE
        hideKeyboard(filenameInput)
        Handler(Looper.getMainLooper()).postDelayed({
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        },100)
    }

    private fun hideKeyboard(view: View){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED

    }

    override fun onTimerTick(duration: String) {
        listVisibilityChange(View.GONE)
        with(binding){
            listNoteAudio.visibility = View.GONE
            tvDurationRecord.visibility = View.VISIBLE
            tvDurationRecord.text = "Идёт запись\nУдерживайте кнопку ${duration.dropLast(3)}"
        }
        recordController.duration = duration.dropLast(3)
    }


    override fun onItemClickListener(imgBtn: ImageButton, tvDuration: TextView, audioNoteParams: AudioNoteParams, position: Int) {
        mediaPlayerController.startPlayerNote(audioNoteParams = audioNoteParams,playIconButton = imgBtn,position = position,tvDuration=tvDuration)
    }


}
