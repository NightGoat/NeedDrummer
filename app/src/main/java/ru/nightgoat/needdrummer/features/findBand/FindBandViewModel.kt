package ru.nightgoat.needdrummer.features.findBand

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.models.Band

class FindBandViewModel: CoreViewModel() {

    val bands = MutableLiveData<List<Band>>()
}