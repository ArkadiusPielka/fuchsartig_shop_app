package com.example.fuchsartig.data.model

import java.util.Date

data class Profile(
    val gender: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val profileImg: String = "",
    val street: String = "",
    val hausNr: String = "",
    val plz: String = "",
    val city: String = "",
    val country: String = "",
    val birthdate: Date? = null,
    val paymentType: String = ""

)
