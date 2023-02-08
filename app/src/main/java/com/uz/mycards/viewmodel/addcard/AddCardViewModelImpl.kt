package com.uz.mycards.viewmodel.addcard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.data.repository.AppRepository
import com.uz.mycards.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCardViewModelImpl @Inject constructor(private val userRepository: AppRepository) : ViewModel(),
    AddCardViewModel {
    override val uploadCardLiveData = MutableLiveData<Response<String>>()

    override fun uploadCards(request: GetCardsResponse) {
        uploadCardLiveData.value = Response.Loading
        userRepository.uploadCard(request){
            uploadCardLiveData.value = it
        }
    }

}