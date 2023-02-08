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
import com.google.android.material.tabs.TabLayout
import com.uz.mycards.R
import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.data.remote.request.GetCardsRequest
import com.uz.mycards.databinding.ScreenMyCardsBinding
import com.uz.mycards.ui.adapter.CardAdapter
import com.uz.mycards.utils.Response
import com.uz.mycards.utils.snackMessage
import com.uz.mycards.viewmodel.mycards.MyCardsViewModel
import com.uz.mycards.viewmodel.mycards.MyCardsViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCardsScreen : Fragment(R.layout.screen_my_cards) {
    private val binding by viewBinding(ScreenMyCardsBinding::bind)
    val viewModel: MyCardsViewModel by viewModels<MyCardsViewModelImpl>()
    private val cardAdapter: CardAdapter by lazy { CardAdapter() }

    private var tabSelectedId: TabLayout.Tab? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun initObservers() = with(viewModel) {
        loadCardsLiveData.observe(this@MyCardsScreen, loadCardObserver)
    }

    private val loadCardObserver = Observer<Response<List<GetCardsResponse>>> {
        when (it) {
            is Response.Loading -> {
                binding.progress.isVisible = true
            }
            is Response.Failure -> {
                binding.progress.isVisible = false
                snackMessage(it.error ?: "Not found")
            }
            is Response.Success -> {
                binding.progress.isVisible = false
                cardAdapter.list = it.data
            }
        }
    }

    private fun initViews() = with(binding) {
        viewModel.loadCards(GetCardsRequest())
        rvCards.adapter = cardAdapter

        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_cardScreen_to_addCardScreen)
        }

        if (tabSelectedId != null) {
            tabLayoutCards.selectTab(tabSelectedId)
        }

        tabLayoutCards.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSelectedId = tab
                when (tabLayoutCards.selectedTabPosition) {
                    0 -> {
                        viewModel.loadCards(GetCardsRequest(0))
                        cardAdapter.list = emptyList()
                    }
                    1 -> {
//                        viewModel.loadCards(GetCardsRequest(1))
                        cardAdapter.list = emptyList()
                        snackMessage(getString(R.string.favorite))
                    }
                    2 -> {
//                        viewModel.loadCards(GetCardsRequest(2))
                        cardAdapter.list = emptyList()
                        snackMessage(getString(R.string.uzcard))
                    }
                    3 -> {
//                        viewModel.loadCards(GetCardsRequest(3))
                        cardAdapter.list = emptyList()
                        snackMessage(getString(R.string.humo))
                    }
                    4 -> {
//                        viewModel.loadCards(GetCardsRequest(4))
                        cardAdapter.list = emptyList()
                        snackMessage(getString(R.string.national_card))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}