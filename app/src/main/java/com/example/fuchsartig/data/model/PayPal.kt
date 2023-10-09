package com.example.fuchsartig.data.model

import com.google.firebase.firestore.DocumentId

data class PayPal(
    @DocumentId val id: String = "paypal",
    val email: String = "",
    val password: String = "",
    val paypalCheck: Boolean = false
)
