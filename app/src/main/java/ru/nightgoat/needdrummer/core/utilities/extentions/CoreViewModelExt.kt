package ru.nightgoat.needdrummer.core.utilities.extentions

import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult

fun CoreViewModel.launchUITryCatch(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    catchBlock: ((Throwable) -> Unit)? = null, tryBlock: suspend CoroutineScope.() -> Unit
) {
    try {
        viewModelScope.launch(viewModelScope.coroutineContext, start, tryBlock)
    } catch (e: Throwable) {
        catchBlock?.invoke(e) ?: handleResult(SResult.ErrorResult.Error(exception = e))
    }
}

fun CoreViewModel.launchAndHandle(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    catchBlock: ((Throwable) -> Unit)? = null, tryBlock: suspend CoroutineScope.() -> AnyResult
) {
    try {
        viewModelScope.launch(viewModelScope.coroutineContext, start) {
            tryBlock().handleAsResult()
        }
    } catch (e: Throwable) {
        catchBlock?.invoke(e) ?: handleResult(SResult.ErrorResult.Error(exception = e))
    }
}

fun CoreViewModel.launchAsyncTryCatch(catchBlock: ((Throwable) -> Unit)? = null, tryBlock: suspend CoroutineScope.() -> Unit) {
    try {
        launchAsync(CoroutineStart.DEFAULT, tryBlock)
    } catch (e: Throwable) {
        catchBlock?.invoke(e) ?: handleResult(SResult.ErrorResult.Error(exception = e))
    }
}

fun CoreViewModel.launchAsync(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO, start, block)
}

inline fun <reified T> CoreViewModel.asyncLiveData(
    noinline block: suspend LiveDataScope<T>.() -> Unit
) = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO, block = block)

fun <T> CoreViewModel.asyncTryCatchLiveData(
    catchBlock: ((Throwable) -> Unit)? = null,
    tryBlock: suspend () -> T
) = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
    try {
        emit(tryBlock())
    } catch (e: Throwable) {
        catchBlock?.invoke(e) ?: handleResult(SResult.ErrorResult.Error(exception = e))
    }
}

fun <T> CoreViewModel.asyncTryCatchMutableLiveData(
    catchBlock: ((Throwable) -> Unit)? = null,
    tryBlock: suspend () -> T
) = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
    try {
        emit(tryBlock())
    } catch (e: Throwable) {
        catchBlock?.invoke(e) ?: handleResult(SResult.ErrorResult.Error(exception = e))
    }
} as MutableLiveData<T>
