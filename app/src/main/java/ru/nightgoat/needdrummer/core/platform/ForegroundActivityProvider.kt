package ru.nightgoat.needdrummer.core.platform

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreMainActivity
import java.lang.ref.WeakReference

class ForegroundActivityProvider {

    val onPauseStateLiveData = MutableLiveData(false)

    private var weekReference: WeakReference<CoreMainActivity>? = null

    fun setActivity(baseMainActivity: CoreMainActivity) {
        weekReference = WeakReference(baseMainActivity)
        onPauseStateLiveData.value = false
    }

    fun clear() {
        onPauseStateLiveData.value = true
        weekReference?.clear()
        weekReference = null
    }

    fun getActivity(): CoreMainActivity? {
        return weekReference?.get()
    }

}