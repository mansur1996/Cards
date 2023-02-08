package com.uz.mycards.viewmodel.addcard

import androidx.lifecycle.LiveData
import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.data.remote.request.GetCardsRequest
import com.uz.mycards.utils.Response

interface AddCardViewModel {
    val uploadCardLiveData: LiveData<Response<String>>

    fun uploadCards(request: GetCardsResponse)
}