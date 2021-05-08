package ru.nightgoat.needdrummer.core

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import pro.krit.core.common.extensions.toNavigateResult
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.ErrorResult
import ru.nightgoat.needdrummer.models.states.ErrorType

abstract class CoreViewModel: ViewModel(), LifecycleObserver {

    val selectedPage = MutableLiveData(0)
    val navigationLiveData = MutableLiveData<SResult.NavigateResult>()
    val resultLiveData = MutableLiveData<SResult.Success<Any>>()
    val errorLiveData = MutableLiveData<SResult.ErrorResult>()
    val loadingLiveData = MutableLiveData<SResult.Loading>()
    val toastLiveData = MutableLiveData<SResult.Toast>()

    fun goBack() {
        navigationLiveData.value = SResult.NavigateResult.NavigateBack
    }

    fun goTo(result: SResult.NavigateResult) {
        navigationLiveData.value = result
    }

    fun goTo(direction: NavDirections) {
        navigationLiveData.value = direction.toNavigateResult()
    }

    fun showLoading() {
        loadingLiveData.postValue(SResult.Loading.Show)
    }

    fun hideLoading() {
        loadingLiveData.postValue(SResult.Loading.Hide)
    }

    /** Handles SResult to a right LiveData, that observes in CoreFragment */
    fun handleResult(result: AnyResult) {
        when (result) {
            is SResult.NavigateResult -> navigationLiveData.postValue(result)
            is SResult.ErrorResult -> errorLiveData.postValue(result)
            is SResult.Success -> resultLiveData.postValue(result)
            is SResult.Loading -> loadingLiveData.postValue(result)
            is SResult.Toast -> toastLiveData.postValue(result)
            else -> Unit
        }
    }

    /** Handles SResult while being in Loading State, after handling hides loading */
    suspend fun doWhileLoading(doFun: suspend () -> AnyResult) {
        showLoading()
        doFun.invoke().handle()
        hideLoading()
    }

    /** Passes SResult to method
     * @see handleResult */
    fun <T: SResult<R>, R: Any> T.handle() {
        handleResult(this)
    }

    open fun getError(message: String, type: ErrorType = ErrorType.ORDINARY) = ErrorResult(
        message = message,
        type = type
    )
}