package ru.nightgoat.needdrummer.features.start

import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.data.sources.local.IAuthPreference
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val authPreference: IAuthPreference
) : CoreViewModel() {

    val isAuthenticated by lazy {
        liveData {
            delay(START_TIMEOUT_MS)
            emit(authPreference.isAuthenticated())
        }
    }

    companion object {
        private const val START_TIMEOUT_MS = 2000L
    }
}