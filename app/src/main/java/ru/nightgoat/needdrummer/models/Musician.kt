package ru.nightgoat.needdrummer.models

data class Musician(
    val name: String,
    val description: String,
    val instruments: MutableSet<Instrument>,
    val lookingForBandGenres: MutableSet<String>
)
