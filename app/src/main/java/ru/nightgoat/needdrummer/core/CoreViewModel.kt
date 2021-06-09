package ru.nightgoat.needdrummer.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.IResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.ErrorResult
import ru.nightgoat.needdrummer.core.utilities.extentions.toNavigateResult
import ru.nightgoat.needdrummer.models.states.ErrorType

abstract class CoreViewModel: ViewModel() {

    val selectedPage = MutableLiveData(0)
    private val _resultFlow: MutableStateFlow<IResult<Any>> = MutableStateFlow(SResult.Empty)
    val resultFlow: StateFlow<IResult<Any>>
        get() = _resultFlow

    fun goBack() {
        _resultFlow.value = SResult.NavigateResult.NavigateBack
    }

    fun goTo(result: SResult.NavigateResult) {
        _resultFlow.value = result
    }

    fun goTo(direction: NavDirections) {
        _resultFlow.value = direction.toNavigateResult()
    }

    fun showLoading() {
        _resultFlow.value = SResult.Loading
    }

    fun hideLoading() {
        _resultFlow.value = SResult.Empty
    }

    /** Handles SResult to a right LiveData, that observes in CoreFragment */
    fun handleResult(result: AnyResult) {
        _resultFlow.value = result
    }

    /** Passes SResult to method
     * @see handleResult */
    fun <T: SResult<R>, R: Any> T.handleAsResult() {
        handleResult(this)
    }

    open fun getError(message: String, type: ErrorType = ErrorType.ORDINARY) = ErrorResult(
        message = message,
        type = type
    )
}