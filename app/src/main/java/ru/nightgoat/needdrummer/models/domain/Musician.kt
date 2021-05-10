package ru.nightgoat.needdrummer.models.domain

import ru.nightgoat.needdrummer.models.util.Name

data class Musician(
    val name: Name,
    val description: String,
    val instruments: MutableSet<Instrument>,
    val lookingForBandGenres: MutableSet<String>
)
