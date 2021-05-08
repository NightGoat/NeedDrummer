package ru.nightgoat.needdrummer.core

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult

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
}