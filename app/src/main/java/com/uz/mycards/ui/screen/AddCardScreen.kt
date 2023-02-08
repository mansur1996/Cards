package com.uz.mycards.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    private var isValidValiditDate = false
    private var isCardNumber = false
    private var isCardDate = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initViews()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun initObservers() = with(viewModel) {
        uploadCardLiveData.observe(this@AddCardScreen, uploadCardObserver)
    }

    private val uploadCardObserver = Observer<Response<String>> {
        when (it) {
            is Response.Loading -> {
                binding.progress.isVisible = true
            }
            is Response.Failure -> {
                binding.progress.isVisible = false
                snackMessage(it.error ?: "Not added")
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

            if (isValidValiditDate){
                val request =
                    GetCardsResponse(card_number = cardNumber, validity_period = validityPeriod)
                viewModel.uploadCards(request)
            }else{
                snackMessage(getString(R.string.invalid_validity_date))
            }
        }

        etCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                check()
                s?.let {
                    isCardNumber = s.isNotEmpty()
                }
            }

        })

        etValidityPeriodOfTheCard.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                check()
                s?.let {
                    isCardDate = s.isNotEmpty()
                }

                var str = s.toString()
                if (str.length == 2) {
                    if (str.toInt() < 32) isValidValiditDate = true
                    else {
                        isValidValiditDate = false
                        etValidityPeriodOfTheCard.error = getString(R.string.invalid_month_day)
                    }
                } else if (str.length == 5) {
                    str = str.substring(str.length - 2)
                    if (str.toInt() < 13) isValidValiditDate = true
                    else {
                        isValidValiditDate = false
                        etValidityPeriodOfTheCard.error = getString(R.string.invalid_month)
                    }
                }
            }
        })
    }

    private fun check() {
        binding.btnContinue.isEnabled = isCardDate && isCardNumber
    }
}