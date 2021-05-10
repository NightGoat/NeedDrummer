package ru.nightgoat.needdrummer.features.account.core

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel

abstract class CoreAuthViewModel: CoreViewModel() {

    val email = MutableLiveData("")
}