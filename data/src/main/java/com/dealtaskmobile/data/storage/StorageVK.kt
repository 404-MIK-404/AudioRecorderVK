package com.dealtaskmobile.data.storage

interface StorageVK {

    fun saveToken(token: String)

    fun getToken(): String

}