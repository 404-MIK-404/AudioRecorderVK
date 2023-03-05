package com.dealtaskmobile.audiorecordervk.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dealtaskmobile.audiorecordervk.R
import com.dealtaskmobile.audiorecordervk.databinding.AudioNoteItemBinding
import com.dealtaskmobile.audiorecordervk.events.OnItemClickListener
import com.dealtaskmobile.data.entity.AudioNote
import com.dealtaskmobile.domain.models.AudioNoteParams
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class NoteAudioAdapter(var listener: OnItemClickListener): RecyclerView.Adapter<NoteAudioAdapter.NoteAudioHolder>() {

    var audioNoteList = ArrayList<AudioNoteParams>()

    var audioNoteListAll = ArrayList<AudioNoteParams>()

    inner class NoteAudioHolder(item: View): RecyclerView.ViewHolder(item),View.OnClickListener {

        val binding = AudioNoteItemBinding.bind(item)

        var imageBtn: ImageButton? = null

        var tvDuration: TextView? = null

        fun bind(audioNote: AudioNoteParams) = with(binding){
            tvTitleName.text = audioNote.nameFile
            tvMeta.text = "00:00/${audioNote.duration}"
            tvDateEnd.text = "Создано: ${audioNote.dateEnd}"
            imageBtn = playAudioNote
            tvDuration = tvMeta
            playAudioNote.setOnClickListener(this@NoteAudioHolder)

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClickListener(position = position, audioNoteParams = audioNoteList.get(position), imgBtn = imageBtn!!,tvDuration=tvDuration!!)
            }
        }
    }



    fun getRecycleDecoration(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean,resources:Resources) : RecyclerViewSwipeDecorator{
        var recyclerDecoration = RecyclerViewSwipeDecorator(c,recyclerView,viewHolder, dX, dY, actionState, isCurrentlyActive)
        recyclerDecoration.setSwipeLeftActionIconId(R.drawable.ic_baseline_delete_24)
        recyclerDecoration.setSwipeLeftActionIconTint(resources.getColor(R.color.white))
        recyclerDecoration.setSwipeLeftBackgroundColor(resources.getColor(com.google.android.material.R.color.design_default_color_error))
        recyclerDecoration.decorate()
        return recyclerDecoration
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAudioHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.audio_note_item,parent,false)
        return NoteAudioHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAudioHolder, position: Int) {
        holder.bind(audioNote = audioNoteList[position])
    }

    override fun getItemCount(): Int {
        return audioNoteList.size
    }

    fun getAudioNoteParams(position: Int): AudioNoteParams{
        return audioNoteListAll.get(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAudioNote(position: Int){
        audioNoteListAll.removeAt(position)
        audioNoteList.clear()
        audioNoteList.addAll(audioNoteListAll)
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addAudioNoteAll(audioNote: List<AudioNoteParams>){
        audioNoteList.addAll(audioNote)
        audioNoteListAll.addAll(audioNote)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAudioNote(audioNote: AudioNoteParams){
        audioNoteList.add(audioNote)
        audioNoteListAll.add(audioNote)
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun filterList(query: String){
        var filterList: ArrayList<AudioNoteParams> = ArrayList()
        for (item in audioNoteListAll){
            if (item.nameFile.lowercase().contains(query.lowercase())){
                filterList.add(item)
            }
        }
        if (filterList.isEmpty()){
            audioNoteList = audioNoteListAll
            notifyDataSetChanged()
        } else {
            audioNoteList = filterList
            notifyDataSetChanged()
        }

    }



}