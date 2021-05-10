package ru.nightgoat.needdrummer.core.utilities.databinding

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.nightgoat.needdrummer.models.domain.Instrument

@BindingAdapter(value = ["chipItems"])
fun setChips(chipGroup: ChipGroup, items: Set<String>) {
    items.forEach {
        val chip = Chip(chipGroup.context)
        chip.text = it
        chipGroup.addView(chip)
    }
}

@BindingAdapter(value = ["chipInstruments"])
fun setChipsForInstruments(chipGroup: ChipGroup, items: Set<Instrument>) {
    items.forEach {
        val chip = Chip(chipGroup.context)
        chip.text = it.name
        chipGroup.addView(chip)
    }
}