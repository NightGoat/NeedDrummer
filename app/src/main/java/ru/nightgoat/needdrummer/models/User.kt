package ru.nightgoat.needdrummer.models

data class User(
    val email: String,
    val musician: Musician? = null,
    val bands: MutableList<Band> = mutableListOf()
)
