package com.dealtaskmobile.domain.usercase

import com.dealtaskmobile.domain.repository.VKRepository

class SetAccessTokenVK(private val vkRepository: VKRepository) {


    fun execute(tkVK: String){
        vkRepository.saveTokVK(tok = tkVK)
    }


}