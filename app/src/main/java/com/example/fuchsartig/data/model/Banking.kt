package com.example.fuchsartig.data.model

import com.google.firebase.firestore.DocumentId

data class Banking(
    @DocumentId val id: String = "banking",
    val bankingOwner: String = "",
    val iban: String = "",
    val bic: String = ""
)
