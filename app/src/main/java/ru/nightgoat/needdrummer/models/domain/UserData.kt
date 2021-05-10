package ru.nightgoat.needdrummer.models.domain

import ru.nightgoat.needdrummer.models.util.Email

data class UserData(
    val email: Email,
    val musician: Musician? = null,
    val bands: MutableList<Band> = mutableListOf()
)

