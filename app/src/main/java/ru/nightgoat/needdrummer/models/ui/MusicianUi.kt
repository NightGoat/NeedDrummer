package ru.nightgoat.needdrummer.models.ui

import ru.nightgoat.needdrummer.models.domain.Instrument

data class MusicianUi(
    val name: String,
    val description: String,
    val instruments: MutableSet<Instrument>,
    val lookingForBandGenres: MutableSet<String>
)
