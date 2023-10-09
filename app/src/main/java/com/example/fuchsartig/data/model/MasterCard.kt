package com.example.fuchsartig.data.model

import com.google.firebase.firestore.DocumentId

data class MasterCard(
    @DocumentId val id: String = "mastercard",
    val cardOwner: String = "",
    val cardNumber: String = "",
    val cardValid: String = "",
    val cardSecurityNumber: String = "",
    val masterCardCheck: Boolean = false

)
