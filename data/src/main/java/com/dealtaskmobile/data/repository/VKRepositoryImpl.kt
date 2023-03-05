package com.dealtaskmobile.data.repository

import com.dealtaskmobile.data.storage.StorageVK
import com.dealtaskmobile.domain.repository.VKRepository

class VKRepositoryImpl(private val storageVK: StorageVK) : VKRepository {


    override fun saveTokVK(tok: String) {
        storageVK.saveToken(token = tok)
    }


}