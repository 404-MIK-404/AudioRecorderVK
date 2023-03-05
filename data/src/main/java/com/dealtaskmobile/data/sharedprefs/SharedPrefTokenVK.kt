package com.dealtaskmobile.data.sharedprefs

import android.content.Context
import com.dealtaskmobile.data.storage.StorageVK



private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val KEY_FIRST_NAME = "VK_TK"
private const val DEFAULT_NAME = "NOPE"

class SharedPrefTokenVK(context: Context) : StorageVK {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_FIRST_NAME, token).apply()
    }

    override fun getToken(): String {
        return sharedPreferences.getString(KEY_FIRST_NAME,"") ?: DEFAULT_NAME
    }


}