package com.dealtaskmobile.audiorecordervk.managers.alterdialogmanager

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class AlterDialogController() {


    private var dialog: AlertDialog.Builder? = null


    fun createAlterDialog(title: String,message: String,positiveButton: String,negativeButton: String,posButton:  DialogInterface.OnClickListener, negButton:  DialogInterface.OnClickListener,context: Context): AlertDialog.Builder{
        dialog = AlertDialog.Builder(context)
        dialog?.setTitle(title)
        dialog?.setMessage(message)
        dialog?.setPositiveButton(positiveButton,posButton)
        dialog?.setNegativeButton(negativeButton,negButton)
        return dialog!!
    }




}