package ru.nightgoat.needdrummer.core.utilities.extentions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> LifecycleOwner.connectLiveData(source: LiveData<out T>, target: MutableLiveData<T>) {
    source.observe(this, {
        target.value = it
    })
}