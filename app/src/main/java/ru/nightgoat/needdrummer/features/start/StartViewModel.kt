package ru.nightgoat.needdrummer.features.start

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.data.sources.local.IAuthPreference
import javax.inject.Inject


class StartViewModel : CoreViewModel(){

    @Inject
    lateinit var authPreference: IAuthPreference

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun getStartPageNavigation() {
        val direction = if(authPreference.isAuthenticated()) {
            StartFragmentDirections.showMainFragment()
        } else {
            StartFragmentDirections.showAuthFragment()
        }
        goTo(direction)
    }

    companion object {
        private const val START_TIMEOUT_MS = 2000L
    }
}