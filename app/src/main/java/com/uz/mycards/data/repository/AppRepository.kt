package com.uz.mycards.data.repository

import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.data.remote.request.GetCardsRequest
import com.uz.mycards.utils.Response

interface AppRepository {
    fun getCardsList(
        request: GetCardsRequest, response: ((Response<List<GetCardsResponse>>) -> Unit)
    )

    fun uploadCard(request: GetCardsResponse, response: ((Response<String>)-> Unit))
}