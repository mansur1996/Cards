package com.uz.mycards.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.uz.mycards.R
import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.databinding.ItemCardBinding
import com.uz.mycards.utils.Utils

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var onItemClickListener: ((GetCardsResponse) -> Unit)? = null
    fun setOnItemClickListener(block: ((GetCardsResponse) -> Unit)) {
        onItemClickListener = block
    }

    var list = listOf<GetCardsResponse>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CardViewHolder(private val binding: ItemCardBinding, val context: Context) :
        ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(list[absoluteAdapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: GetCardsResponse) = with(binding) {
            Log.e("TAG", "bind: $item", )
            if (item.main) tvIsMain.text = context.getString(R.string.main)
            tvBalance.text = Utils.numberFormatterWithWhiteSpace(item.balance,2) + " " + context.getString(R.string.sum)
            val length = item.card_number.length
            val cardNumber = item.card_number.substring(0, 7) + "** **** " +item.card_number.substring(length-4)
            tvCardNumber.text = cardNumber
            tvValidityDate.text = item.validity_period
            tvCardOwnerName.text = item.card_owner_name
            when(item.bank){
                0->{
                    tvBank.text = context.getString(R.string.xalq_bank)
                    bgCard.setBackgroundResource(R.color.color_xb)
                }
                1->{
                    tvBank.text = context.getString(R.string.sqb)
                    bgCard.setBackgroundResource(R.color.color_sqb)
                }
                2->{
                    tvBank.text = context.getString(R.string.uz_milliy_bank)
                    bgCard.setBackgroundResource(R.color.color_uzb)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}