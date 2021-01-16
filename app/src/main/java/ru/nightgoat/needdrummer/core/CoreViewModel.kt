package ru.nightgoat.needdrummer.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lenta.shared.utilities.Logg
import ru.nightgoat.needdrummer.core.platform.Either
import ru.nightgoat.needdrummer.core.platform.Failure
import ru.nightgoat.needdrummer.core.platform.SingleLiveEvent

abstract class CoreViewModel: ViewModel() {

    val failure = SingleLiveEvent<Failure>()
    val selectedPage = MutableLiveData(0)

    open fun handleFailure(failure: Failure) {
        Logg.e { "handleFailure: $failure" }
        this.failure.postValue(failure)
    }

    fun <L: Failure, R> Either<L, R>.handleFailureOrRight(fn: (R) -> Unit) {
        return when (this) {
            is Either.Left -> handleFailure(this.a)
            is Either.Right -> fn(this.b)
        }
    }
}