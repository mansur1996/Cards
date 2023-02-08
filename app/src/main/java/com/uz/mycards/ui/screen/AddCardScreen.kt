package com.uz.mycards.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.uz.mycards.R
import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.databinding.ScreenAddCardBinding
import com.uz.mycards.utils.Response
import com.uz.mycards.utils.snackMessage
import com.uz.mycards.viewmodel.addcard.AddCardViewModel
import com.uz.mycards.viewmodel.addcard.AddCardViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardScreen : Fragment(R.layout.screen_add_card) {

    private val binding by viewBinding(ScreenAddCardBinding::bind)
    private val viewModel: AddCardViewModel by viewModels<AddCardViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initViews()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun initObservers() = with(viewModel){
        uploadCardLiveData.observe(this@AddCardScreen, uploadCardObserver)
    }

    private val uploadCardObserver = Observer<Response<String>> {
        when(it){
            is Response.Loading -> {
                binding.progress.isVisible = true
            }
            is Response.Failure -> {
                binding.progress.isVisible = false
                snackMessage(it.error?:"Not added")
            }
            is Response.Success -> {
                binding.progress.isVisible = false
                binding.etCardNumber.text?.clear()
                binding.etValidityPeriodOfTheCard.text?.clear()
                snackMessage("Successfully added")
            }
        }
    }

    private fun initViews() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnScan.setOnClickListener {
            snackMessage("Clicked")
        }

        btnContinue.setOnClickListener {
            val cardNumber = etCardNumber.text.toString().trim()
            val validityPeriod = etValidityPeriodOfTheCard.text.toString().trim()

            if (cardNumber.isNotEmpty() && validityPeriod.isNotEmpty()){
                val request = GetCardsResponse(card_number = cardNumber, validity_period = validityPeriod)
                viewModel.uploadCards(request)
            }else{
                snackMessage("Field not filled")
            }
        }
    }
}