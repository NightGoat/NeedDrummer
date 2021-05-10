package ru.nightgoat.needdrummer.models.domain

data class Band(
    val name: String,
    val description: String = "",
    val genres: MutableSet<String> = mutableSetOf(),
    val links: MutableSet<String> = mutableSetOf(),
    val needInstruments: MutableSet<Instrument>
)
