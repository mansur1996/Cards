package com.uz.mycards.viewmodel.mycards

import androidx.lifecycle.MutableLiveData
import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.data.remote.request.GetCardsRequest
import com.uz.mycards.data.repository.AppRepository
import androidx.lifecycle.ViewModel
import com.uz.mycards.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyCardsViewModelImpl  @Inject constructor(private val appRepository: AppRepository) : ViewModel(),
    MyCardsViewModel {
    override val loadCardsLiveData = MutableLiveData<Response<List<GetCardsResponse>>>()

    override fun loadCards(request: GetCardsRequest) {
        loadCardsLiveData.value = Response.Loading
        appRepository.getCardsList(request){
            loadCardsLiveData.value = it
        }
    }

}