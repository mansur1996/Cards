package com.uz.mycards.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.uz.mycards.data.remote.reponse.GetCardsResponse
import com.uz.mycards.data.remote.request.GetCardsRequest
import com.uz.mycards.utils.Response
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    val db: FirebaseFirestore
) : AppRepository {
    override fun getCardsList(
        request: GetCardsRequest,
        response: (Response<List<GetCardsResponse>>) -> Unit
    ) {
        db.collection("cards")
            .get()
            .addOnSuccessListener { result->
                val cards = mutableListOf<GetCardsResponse>()
                for (document in result) {
                    cards.add(document.toObject(GetCardsResponse::class.java))
                }
                response.invoke(Response.Success(cards))
            }
            .addOnFailureListener {exception->
                response.invoke(Response.Failure(exception.localizedMessage))
            }
    }

    override fun uploadCard(request: GetCardsResponse, response: (Response<String>) -> Unit) {
        db.collection("cards")
            .document()
            .set(request)
            .addOnSuccessListener { documentReference ->
                response.invoke(Response.Success("Added"))
            }
            .addOnFailureListener { exception ->
                response.invoke(Response.Failure(exception.localizedMessage))
            }
    }
}