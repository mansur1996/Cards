package com.uz.mycards.viewmodel.mycards

import androidx.lifecycle.LiveData
import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.data.remote.request.GetCardsRequest
import com.uz.mycards.utils.Response

interface MyCardsViewModel {
    val loadCardsLiveData: LiveData<Response<List<GetCardsResponse>>>

    fun loadCards(request: GetCardsRequest)
}