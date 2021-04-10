package ru.nightgoat.needdrummer.core

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lenta.shared.utilities.Logg
import ru.nightgoat.needdrummer.core.platform.models.Failure
import ru.nightgoat.needdrummer.core.platform.models.NavigationResult

abstract class CoreViewModel: ViewModel(), LifecycleObserver {

    val failure = MutableLiveData<Failure>()
    val selectedPage = MutableLiveData(0)
    val navigationLiveData = MutableLiveData<NavigationResult>()

    open fun handleFailure(failure: Failure) {
        Logg.e { "handleFailure: $failure" }
        this.failure.postValue(failure)
    }

    fun goBack() {
        navigationLiveData.value = NavigationResult.NavigateBack
    }
}