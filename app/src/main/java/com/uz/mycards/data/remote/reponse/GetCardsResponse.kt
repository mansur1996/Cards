package com.uz.mycards.data.remote.reponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCardsResponse(
    val card_number : String,
    val validity_period : String,
    val balance : Double = 0.00,
    val card_owner_name : String = "Mirzayev Mansur",
    val bank : Int = 0,
    val main : Boolean = false

) : Parcelable{
    constructor():this("", "", 0.00,"",0,false)
}
